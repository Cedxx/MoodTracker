package cedric.druesnes.moodtracker.Controller;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import cedric.druesnes.moodtracker.R;

public class HistoryActivity extends AppCompatActivity {

    private int mColor = 3;
    private ImageButton mImageColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);
        mImageColor = findViewById(R.id.imageButton);

        TextView textView = findViewById(R.id.commentMood);
        textView.setText(getIntent().getStringExtra("MOOD_COMMENT"));

//        TextView moodIndexText = findViewById(R.id.moodIndexText);
//        Integer moodIndex = getIntent().getIntExtra("MOOD_INDEX", 3);
//        moodIndexText.setText(moodIndex.toString());

        View moodIndexColor = findViewById(R.id.imageButton);
        Integer moodIndex = getIntent().getIntExtra("MOOD_INDEX", 3);
        moodIndexColor.setBackgroundColor(moodIndex);



    }

    //Mood background color
    private int moodBackground (int color){
        if (color < 0){
            color = 0;
        } else if (color > 4){
            color = 4;
        }
        mColor = color;
        switch (color){
            case 0:
                mImageColor.setBackgroundColor(getResources().getColor(R.color.faded_red));
                break;
            case 1:
                mImageColor.setBackgroundColor(getResources().getColor(R.color.warm_grey));
                break;
            case 2:
                mImageColor.setBackgroundColor(getResources().getColor(R.color.cornflower_blue_65));
                break;
            case 3:
                mImageColor.setBackgroundColor(getResources().getColor(R.color.light_sage));
                break;
            case 4:
                mImageColor.setBackgroundColor(getResources().getColor(R.color.banana_yellow));
                break;
        }
        return color;

    }
}
