package cedric.druesnes.moodtracker.Controller;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import cedric.druesnes.moodtracker.R;

public class HistoryActivity extends AppCompatActivity {


    private ConstraintLayout mHistoryLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Linking the elements in the layout to Java code
        setContentView(R.layout.activity_history);
         mHistoryLayout = findViewById(R.id.historyConstrainLayout);

        //getting the Intent from the MainActivity for the comment button and display the comment
        TextView textView = findViewById(R.id.commentMood);
        textView.setText(getIntent().getStringExtra("MOOD_COMMENT"));

        //get the Mood color to display the current Mood when History Button is press
        View moodColor = findViewById(R.id.commentMood);
        Integer moodIndex = getIntent().getIntExtra("MOOD_INDEX", 3);
        moodColor.setBackgroundColor(moodBackground(moodIndex));



    }

    //Mood background color switch
    private int moodBackground (int moodIndex){
        switch (moodIndex){
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
        return moodIndex;

    }
}
