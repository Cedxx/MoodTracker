package cedric.druesnes.moodtracker.Controller;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cedric.druesnes.moodtracker.R;
import cedric.druesnes.moodtracker.view.MyRecylerViewAdapter;

public class HistoryActivity extends AppCompatActivity {


    private LinearLayout mRowLayout;
    String[] myComment;
    //RecyclerView variable :
    MyRecylerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Linking the elements in the layout to Java code
        setContentView(R.layout.activity_history);
        mRowLayout = findViewById(R.id.row_layout);

        //getting the Intent from the MainActivity for the comment button and display the comment
        //TextView textView = findViewById(R.id.recyclerComment);
        //textView.setText(getIntent().getStringExtra("MOOD_COMMENT"));

        //Integer moodIndex = getIntent().getIntExtra("MOOD_INDEX", 3);
        //moodBackground(moodIndex);

//        Intent intent = getIntent();
//        String[] stringArray = getIntent().getStringArrayExtra("string");

        // data to populate the RecyclerView with
        myComment = getIntent().getStringArrayExtra("MOOD_COMMENT");
//        Integer moodIndex = getIntent().getIntExtra("MOOD_INDEX", 3);
//        moodBackground(moodIndex);
//        List<String[]> commentHistory = new ArrayList<>();
//        commentHistory.add(myComment);
//        commentHistory.add(myComment);
//        commentHistory.add(myComment);
//        commentHistory.add(myComment);
//        commentHistory.add(myComment);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyRecylerViewAdapter(this, myComment);
        recyclerView.setAdapter(mAdapter);

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
