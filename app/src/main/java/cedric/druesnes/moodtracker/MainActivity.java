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
    private View.OnTouchListener mListener;


    //Images variable that will be display during OnFling Action
    int[] mImages = {R.drawable.smiley_super_happy,
            R.drawable.smiley_happy,
            R.drawable.smiley_normal,
            R.drawable.smiley_disappointed,
            R.drawable.smiley_sad};
    int MIN_DISTANCE = 150;
    int OFF_PATH = 100;
    int VELOCITY_THRESHOLD = 75;
    int mImageIndex = 0;
    // end of Images variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this is the view we will add the gesture detector to
        mMoodImage = findViewById(R.id.moodView);
        mMoodImage.setOnTouchListener(mListener);

        // get the gesture detector
        mDetector = new GestureDetector(this, new MyGestureListener());

        //add a touch listener to the view
        // the touch lister passes all its events on to the gesture detector
        mListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mDetector.onTouchEvent(event);
            }
        };
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

            if (Math.abs(event1.getY() - event2.getY()) > OFF_PATH)
                return false;

            if (images.length != 0) {
                if (event1.getX() - event2.getX() > MIN_DISTANCE && Math.abs(velocityX) > VELOCITY_THRESHOLD) {
                    // Swipe left
                    mImageIndex++;
                    if (mImageIndex == images.length)
                        mImageIndex = 0;
                    mMoodImage.setImageResource(mImages[mImageIndex]);
                } else {
                    // Swipe right
                    if (event2.getX() - event1.getX() > MIN_DISTANCE && Math.abs(velocityX) > VELOCITY_THRESHOLD) {
                        mImageIndex--;
                        if (mImageIndex < 0) mImageIndex =
                                images.length - 1;
                        mMoodImage.setImageResource(mImages[mImageIndex]);
                    }
                }
            }

            return true;
        }
    }
}

