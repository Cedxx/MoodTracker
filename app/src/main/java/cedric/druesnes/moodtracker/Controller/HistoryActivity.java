package cedric.druesnes.moodtracker.Controller;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import cedric.druesnes.moodtracker.Model.Mood;
import cedric.druesnes.moodtracker.Model.MoodDbHelper;
import cedric.druesnes.moodtracker.Model.MoodModel;
import cedric.druesnes.moodtracker.R;
import cedric.druesnes.moodtracker.view.MyRecyclerViewAdapter;

//HistoryActivity will display the history of the saved mood for the last 7 days
public class HistoryActivity extends AppCompatActivity {


    //RecyclerView variable :
    MyRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instantiate the database
        MoodDbHelper dbHelper = new MoodDbHelper(getApplicationContext());

        // Get the database repository in read mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                Mood.MoodEntry.COLUMN_COMMENT,
                Mood.MoodEntry.COLUMN_MOOD_INDEX,
                Mood.MoodEntry.COLUMN_DATE
        };


        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Mood.MoodEntry.COLUMN_DATE + " DESC";

        Cursor cursor = db.query(
                Mood.MoodEntry.TABLE_NAME,   //The table to query
                projection,                  // The array of columns to return (pass null to get all)
                null,                   // The columns for the WHERE clause
                null,               //The values for the WHERE clause
                null,                //Don't group the rows
                null,                 //Don't filter by row groups
                sortOrder                   //The sort order
        );

        //set the information from the database to the ArrayList and retrieve them
        ArrayList<MoodModel> moods = new ArrayList<>();
        while (cursor.moveToNext()) {
            MoodModel mood = new MoodModel();
            mood.setComment(cursor.getString(cursor.getColumnIndex(Mood.MoodEntry.COLUMN_COMMENT)));
            mood.setMoodIndex(cursor.getInt(cursor.getColumnIndex(Mood.MoodEntry.COLUMN_MOOD_INDEX)));
            moods.add(0,mood);
        }
        cursor.close();

        //Fill up the rows with empty color if no mood was save during the last 7 days
        for (int i = 0; moods.size() < 7; i++) {
            MoodModel mood = new MoodModel();
            mood.setComment("");
            mood.setMoodIndex(10);
            moods.add(0, mood);
        }


        // Linking the elements in the layout to Java code
        setContentView(R.layout.activity_history);


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyRecyclerViewAdapter(this, moods);
        recyclerView.setAdapter(mAdapter);

    }

}
