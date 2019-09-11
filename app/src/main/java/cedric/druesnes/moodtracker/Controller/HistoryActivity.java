package cedric.druesnes.moodtracker.Controller;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import cedric.druesnes.moodtracker.R;
import cedric.druesnes.moodtracker.view.MyRecylerViewAdapter;

public class HistoryActivity extends AppCompatActivity {


    private ConstraintLayout mHistoryLayout;
    //RecyclerView variable :
    MyRecylerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Linking the elements in the layout to Java code
        setContentView(R.layout.activity_history);
        //mHistoryLayout = findViewById(R.id.historyConstrainLayout);

        //getting the Intent from the MainActivity for the comment button and display the comment
        //TextView textView = findViewById(R.id.recyclerComment);
        //textView.setText(getIntent().getStringExtra("MOOD_COMMENT"));

        //Integer moodIndex = getIntent().getIntExtra("MOOD_INDEX", 3);
        //moodBackground(moodIndex);

        // data to populate the RecyclerView with
        String myComment = getIntent().getStringExtra("MOOD_COMMENT");
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add(myComment);
        animalNames.add(myComment);
        animalNames.add(myComment);
        animalNames.add(myComment);
        animalNames.add(myComment);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyRecylerViewAdapter(this, animalNames);
        recyclerView.setAdapter(mAdapter);

    }

    //Mood background color switch
    private void moodBackground(int moodIndex) {
        switch (moodIndex) {
            case 0:
                mHistoryLayout.setBackgroundColor(getResources().getColor(R.color.faded_red));
                break;
            case 1:
                mHistoryLayout.setBackgroundColor(getResources().getColor(R.color.warm_grey));
                break;
            case 2:
                mHistoryLayout.setBackgroundColor(getResources().getColor(R.color.cornflower_blue_65));
                break;
            case 3:
                mHistoryLayout.setBackgroundColor(getResources().getColor(R.color.light_sage));
                break;
            case 4:
                mHistoryLayout.setBackgroundColor(getResources().getColor(R.color.banana_yellow));
                break;
        }

    }
}
