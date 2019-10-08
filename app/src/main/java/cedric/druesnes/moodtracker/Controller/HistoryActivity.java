package cedric.druesnes.moodtracker.Controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cedric.druesnes.moodtracker.Model.Mood;
import cedric.druesnes.moodtracker.Model.MoodDbHelper;
import cedric.druesnes.moodtracker.Model.MoodModel;
import cedric.druesnes.moodtracker.R;
import cedric.druesnes.moodtracker.view.MyRecylerViewAdapter;

public class HistoryActivity extends AppCompatActivity {


    private LinearLayout mRowLayout;
    String[] myComment;
    //RecyclerView variable :
    MyRecylerViewAdapter mAdapter;
    ArrayList<MoodModel> mComment;


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

        ArrayList<MoodModel> moods = new ArrayList<MoodModel>();
        while (cursor.moveToNext()) {
            MoodModel mood = new MoodModel();
            mood.setComment(cursor.getString(cursor.getColumnIndex(Mood.MoodEntry.COLUMN_COMMENT)));
            mood.setMoodIndex(cursor.getInt(cursor.getColumnIndex(Mood.MoodEntry.COLUMN_MOOD_INDEX)));
            moods.add(mood);
        }
        cursor.close();


        // Linking the elements in the layout to Java code
        setContentView(R.layout.activity_history);
        mRowLayout = findViewById(R.id.row_layout);


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyRecylerViewAdapter(this, moods);
        recyclerView.setAdapter(mAdapter);

        //Vertical divider for the RecyclerView when displaying history of the mood
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

    }

    //Mood background color switch
    private void moodBackground(int moodIndex) {
        switch (moodIndex) {
            case 0:
                mRowLayout.setBackgroundColor(getResources().getColor(R.color.faded_red));
                break;
            case 1:
                mRowLayout.setBackgroundColor(getResources().getColor(R.color.warm_grey));
                break;
            case 2:
                mRowLayout.setBackgroundColor(getResources().getColor(R.color.cornflower_blue_65));
                break;
            case 3:
                mRowLayout.setBackgroundColor(getResources().getColor(R.color.light_sage));
                break;
            case 4:
                mRowLayout.setBackgroundColor(getResources().getColor(R.color.banana_yellow));
                break;
        }

    }
}
