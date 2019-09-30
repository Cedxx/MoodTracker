package cedric.druesnes.moodtracker.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static cedric.druesnes.moodtracker.Model.Mood.SQL_CREATE_ENTRIES;
import static cedric.druesnes.moodtracker.Model.Mood.SQL_DELETE_ENTRIES;

public class MoodDbHelper extends SQLiteOpenHelper {
    //If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MoodDB.db";

    public MoodDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Mood.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(Mood.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDownGrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
