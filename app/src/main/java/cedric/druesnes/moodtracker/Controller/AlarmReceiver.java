package cedric.druesnes.moodtracker.Controller;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cedric.druesnes.moodtracker.Model.Mood;
import cedric.druesnes.moodtracker.Model.MoodDbHelper;

class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        //Retrieve SharedPreferences
        SharedPreferences pref = context.getSharedPreferences("MyprefsFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        //Retrieve SharedPreferences data for the mood and manual entry
        int currentMood = pref.getInt("mood", 0);
        boolean manualEntry = pref.getBoolean("manual", false);

        //set in the database the mood the user last selected
        if (!manualEntry){
            //Instantiate the database
            MoodDbHelper dbHelper = new MoodDbHelper(context);

            // Get the database repository in write mode
            SQLiteDatabase databaseWrite = dbHelper.getWritableDatabase();

            //Create the simple date format that will be used for the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = new Date();

            //Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(Mood.MoodEntry.COLUMN_COMMENT, "");
            values.put(Mood.MoodEntry.COLUMN_MOOD_INDEX, currentMood);
            values.put(Mood.MoodEntry.COLUMN_DATE, dateFormat.format(date));

            //Insert the new row, returning the primary key value of the new row
            databaseWrite.insert(Mood.MoodEntry.TABLE_NAME, null, values);
        }
        editor.putInt("mood", 0);
        editor.putBoolean("manual", false);
        editor.commit();
    }
}
