package cedric.druesnes.moodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // Member variable
    private static final int SWIPE_MIN_DISTANCE = 60;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;
    private GestureDetector mDetector;
    private MyGestureListener mListener;
    public ImageView mMoodImage;
    public ImageButton mCommentImage;
    public ImageButton mHistoryButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListener = new MyGestureListener();
        mDetector = new GestureDetector(getApplicationContext(), mListener);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDetector.onTouchEvent(event)){
            return false;
        }else {
            return true;
        }
    }
}

