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
    public ImageView mMoodImage;
    public ImageButton mCommentImage;
    public ImageButton mHistoryButton;
    private GestureDetector mDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Linked the elements in the layout to Java code
        mMoodImage = findViewById(R.id.moodView);
        mCommentImage = findViewById(R.id.commentButton);
        mHistoryButton = findViewById(R.id.historyButton);

        // Image mood variable
        final int[] moodArray = {R.drawable.smiley_super_happy,
                R.drawable.smiley_happy,
                R.drawable.smiley_normal,
                R.drawable.smiley_disappointed,
                R.drawable.smiley_sad};

        //this is the view we will add the gesture detector to
        View myView = findViewById(R.id.moodView);

        // get the gesture detector
        mDetector = new GestureDetector(this, new MyGestureListener());

        //add a touch listener to the view
        // the touch lister passes all its events on to the gesture detector
        myView.setOnTouchListener(touchListener);
    }

    // This touch listener passes everything on to the gesture detector.
    // That saves us the trouble of interpreting the raw touch events
    // ourselves.
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // pass the events to the gesture detector
            // a return value of true means the detector is handling it
            // a return value of false means the detector didn't
            // recognize the event
            return mDetector.onTouchEvent(event);
        }
    };


    // In the SimpleOnGestureListener subclass you should override onDown and any other gesture that you want to detect.
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) { // MUST return true here or else none of the other gestures will work
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("TAG", "onFling: ");
            return true;
        }
    }
}

