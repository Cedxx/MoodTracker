package cedric.druesnes.moodtracker.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;

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

        // Linking the elements in the layout to Java code
        setContentView(R.layout.activity_history);
        mRowLayout = findViewById(R.id.row_layout);

//        //getting the Intent from the MainActivity for the background color of the current mood
//        Integer moodIndex = getIntent().getIntExtra("MOOD_INDEX", 3);
//        moodBackground(moodIndex);

        //getting the Intent from the MainActivity for the comment button and display the comment
        myComment = getIntent().getStringArrayListExtra("MOOD_COMMENT").toArray(new String[0]);
        mComment = new ArrayList<>();
        mComment.add(new MoodModel());

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyRecylerViewAdapter(this, myComment);
        recyclerView.setAdapter(mAdapter);

        //Vertical divider for the RecyclerView when displaying history of the mood
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));

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
