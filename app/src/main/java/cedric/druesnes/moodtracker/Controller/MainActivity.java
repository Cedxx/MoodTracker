package cedric.druesnes.moodtracker.Controller;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Comment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cedric.druesnes.moodtracker.Model.Mood;
import cedric.druesnes.moodtracker.Model.MoodDbHelper;
import cedric.druesnes.moodtracker.Model.MoodModel;
import cedric.druesnes.moodtracker.R;
import cedric.druesnes.moodtracker.view.MyRecylerViewAdapter;

public class MainActivity extends AppCompatActivity {


    // Constants for onFling method:
    private static final int SWIPE_MIN_DISTANCE = 60;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    // Constants for the SoundPool
    private final int NR_OF_SIMULTANEOUS_SOUNDS = 1;
    private final float LEFT_VOLUME = 1.0f;
    private final float RIGHT_VOLUME = 1.0f;
    private final int NO_LOOP = 0;
    private final int PRIORITY = 0;
    private final float NORMAL_PLAY_RATE = 1.0f;

    // Member variable
    private GestureDetector mDetector;
    private MyGestureListener mListener;
    public ImageView mMoodImage;
    public ImageButton mCommentImage;
    public ImageButton mHistoryButton;
    private int mCurrentMood = 3;
    private ConstraintLayout mConstraintLayout;
    private AlertDialog mComment;
    private MoodModel mMood;
    private SoundPool mSoundPool;
    private int mASoundId;
    private ArrayList<String> mCommentArray;

    //Shared preferences variable :
    private static final String PREFS = "MyPrefsFile";
    public static final String PREF_KEY_COMMENT = "PREF_KEY_COMMENT";
    private SharedPreferences mPreferences;
    private SharedPreferences mRetrievePreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListener = new MyGestureListener();
        mDetector = new GestureDetector(getApplicationContext(), mListener);
        mMood = new MoodModel();
        mMood.setMoodIndex(mCurrentMood);


        // Linking the elements in the layout to Java code
        mMoodImage = findViewById(R.id.moodView);
        mCommentImage = findViewById(R.id.commentButton);
        mHistoryButton = findViewById(R.id.historyButton);
        mConstraintLayout = findViewById(R.id.contrainLayout);

        //OnClickListener for the comment button
        mCommentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComment();
            }
        });

        //Create an empty ArrayList for the CommentButton
        mCommentArray = new ArrayList<>();

        //OnClickListener for the history button that will open a new activity for the history
        //Create a new Intent to send the comment and the background color from the user to the History Activity
        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivityIntent = new Intent(MainActivity.this, HistoryActivity.class);
                historyActivityIntent.putStringArrayListExtra("MOOD_COMMENT", mCommentArray);
                historyActivityIntent.putExtra("MOOD_INDEX", mMood.getMoodIndex());
                startActivity(historyActivityIntent);

            }
        });

        //create a new SoundPool when we swipe mood
        mSoundPool = new SoundPool(NR_OF_SIMULTANEOUS_SOUNDS, AudioManager.STREAM_MUSIC, 0);

        //Load and get the IDs to identify the sounds
        mASoundId = mSoundPool.load(getApplicationContext(), R.raw.note6_a, 1);

        //Saving the user information with SharedPreferences
//        mPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
//        SharedPreferences.Editor editor = mPreferences.edit();
//        AlertDialog myComment = mComment;
//        editor.putString(PREF_KEY_COMMENT, String.valueOf(myComment));
//        editor.apply();
//        retrievePreferences();


    }


//    private void retrievePreferences() {
//        // Calling the shared preference
//        mRetrievePreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
//        String userInput = mRetrievePreferences.getString("PREF_KEY_COMMENT", "");
//        Log.i("CheckMyStringIsOK", userInput); //Le the string to be sure we get the correct value
//
//
//    }


    //AlertDialog for the comment button
    public void showComment() {
        final EditText editText = new EditText(this);
        if (mComment == null) {
            mComment = new AlertDialog.Builder(this)
                    .setTitle("Commentaire")
                    .setView(editText)
                    .setPositiveButton("VALIDER", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Instantiate the database
                            MoodDbHelper dbHelper = new MoodDbHelper(getApplicationContext());
                            // Get the database repository in write mode
                            SQLiteDatabase db = dbHelper.getWritableDatabase();

                            //Create a new map of values, where column names are the keys
                            ContentValues values = new ContentValues();
                            values.put(Mood.MoodEntry.COLUMN_COMMENT, editText.getText().toString());
                            values.put(Mood.MoodEntry.COLUMN_MOOD_INDEX, mMood.getMoodIndex());

                            //Insert the new row, returning the primary key value of the new row
                            long newRowId = db.insert(Mood.MoodEntry.TABLE_NAME, null, values);

                            //send the comment to the historyActivity
                            mCommentArray.add(editText.getText().toString());
                            editText.setText("", TextView.BufferType.EDITABLE);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
        }
        mComment.show();
    }


    //Mood Image variable
    private int changeMood(int currentMood) {
        if (currentMood < 0) {
            currentMood = 0;
        } else if (currentMood > 4) {
            currentMood = 4;
        }
        mCurrentMood = currentMood;
        mMood.setMoodIndex(currentMood);
        switch (currentMood) {
            case 0:
                mMoodImage.setImageResource(R.drawable.smiley_sad);
                mConstraintLayout.setBackgroundColor(getResources().getColor(R.color.faded_red));
                mSoundPool.play(mASoundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
                break;
            case 1:
                mMoodImage.setImageResource(R.drawable.smiley_disappointed);
                mConstraintLayout.setBackgroundColor(getResources().getColor(R.color.warm_grey));
                mSoundPool.play(mASoundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
                break;
            case 2:
                mMoodImage.setImageResource(R.drawable.smiley_normal);
                mConstraintLayout.setBackgroundColor(getResources().getColor(R.color.cornflower_blue_65));
                mSoundPool.play(mASoundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
                break;
            case 3:
                mMoodImage.setImageResource(R.drawable.smiley_happy);
                mConstraintLayout.setBackgroundColor(getResources().getColor(R.color.light_sage));
                mSoundPool.play(mASoundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
                break;
            case 4:
                mMoodImage.setImageResource(R.drawable.smiley_super_happy);
                mConstraintLayout.setBackgroundColor(getResources().getColor(R.color.banana_yellow));
                mSoundPool.play(mASoundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
                break;
        }
        return currentMood;

    }

    //Generate the current date
    private String todayDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ROOT);
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    // TODO: Add onResume() here:

    // TODO: Add onPause() here:


    // onTouchEven to handle the swipe view
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDetector.onTouchEvent(event)) {
            return false;
        } else {
            return true;
        }
    }

    //Gesture Detector to handle Swipe action with onFling
    public class MyGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH || Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH)
                    return false;

                if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    changeMood(--mCurrentMood);
                    // bottom to top
                } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    changeMood(++mCurrentMood);
                    // top to bottom
                }

            } catch (Exception e) {
                // nothing
            }

            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }
    }


}

