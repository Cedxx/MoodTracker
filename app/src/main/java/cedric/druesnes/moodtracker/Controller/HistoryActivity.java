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

//    private int mColor;
//    private Layout mImageColor;
    private ConstraintLayout mHistoryLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);
        //mImageColor = findViewById(R.id.imageButton);
         mHistoryLayout = findViewById(R.id.historyConstrainLayout);

        TextView textView = findViewById(R.id.commentMood);
        textView.setText(getIntent().getStringExtra("MOOD_COMMENT"));

//        TextView moodIndexText = findViewById(R.id.moodIndexText);
//        Integer moodIndex = getIntent().getIntExtra("MOOD_INDEX", 3);
//        moodIndexText.setText(moodIndex.toString());

        View moodColor = findViewById(R.id.historyConstrainLayout);
        Integer moodIndex = getIntent().getIntExtra("MOOD_INDEX", 3);
        moodColor.setBackgroundColor(moodBackground(moodIndex));



    }

    //Mood background color
    private int moodBackground (int moodIndex){
//        if (moodIndex < 0){
//            moodIndex = 0;
//        } else if (moodIndex > 4){
//            moodIndex = 4;
//        }
//        mColor = moodIndex;
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
