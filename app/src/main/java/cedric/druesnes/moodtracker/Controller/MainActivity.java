package cedric.druesnes.moodtracker.Controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cedric.druesnes.moodtracker.Model.Mood;
import cedric.druesnes.moodtracker.Model.MoodDbHelper;
import cedric.druesnes.moodtracker.Model.MoodModel;
import cedric.druesnes.moodtracker.R;

public class MainActivity extends AppCompatActivity {


    // Constants for onFling method:
    private static final int SWIPE_MIN_DISTANCE = 60;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;
    private static SQLiteDatabase mDatabaseRead;
    private static SQLiteDatabase mDatabaseWrite;

    // Member variable
    private GestureDetector mDetector;
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
    private MoodDbHelper mDbHelper;


    // SharedPreferences variable
    public static final String MyPref = "MyPrefsFile";
    protected SharedPreferences.Editor mEditor;
    protected SharedPreferences mSharedPreferences;

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyGestureListener listener = new MyGestureListener();
        mDetector = new GestureDetector(getApplicationContext(), listener);
        mMood = new MoodModel();
        mMood.setMoodIndex(mCurrentMood);

        //Instantiate the database
        mDbHelper = new MoodDbHelper(getApplicationContext());

        // Get the database repository in write mode
        mDatabaseWrite = mDbHelper.getWritableDatabase();
        // Get the database repository in read mode
        mDatabaseRead = mDbHelper.getReadableDatabase();


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
                startActivity(historyActivityIntent);

            }
        });

        //create a new SoundPool when we swipe mood
        // Constants for the SoundPool
        int NR_OF_SIMULTANEOUS_SOUNDS = 1;
        mSoundPool = new SoundPool(NR_OF_SIMULTANEOUS_SOUNDS, AudioManager.STREAM_MUSIC, 0);

        //Load and get the IDs to identify the sounds
        mASoundId = mSoundPool.load(getApplicationContext(), R.raw.note6_a, 1);

        //Calling the getMoodOlderThan7Days method to check if their are entry older then 7 days in the DB and remove them.
        getMoodOlderThan7Days();

        //Retrieve SharedPreferences data
        mSharedPreferences = getSharedPreferences(MyPref, MODE_PRIVATE);
        int saveValue = mSharedPreferences.getInt("mood", 0);
        changeMood(saveValue);


        //Set default SharedPreferences to False when the user did not typed in a comment
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean("manual", false);
        mEditor.commit();


        AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE,1);
        calendar.set(Calendar.SECOND,0);

        alarmMgr.setRepeating(AlarmManager.RTC,calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

    }


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

                            //Calling the isNotOlderThanAWeek method to check if their is already a mood set for the day
                            //and remove the current mood to receive the new entry
                            isNotOlderThanAWeek();

                            //Create a new map of values, where column names are the keys
                            ContentValues values = new ContentValues();
                            values.put(Mood.MoodEntry.COLUMN_COMMENT, editText.getText().toString());
                            values.put(Mood.MoodEntry.COLUMN_MOOD_INDEX, mMood.getMoodIndex());
                            values.put(Mood.MoodEntry.COLUMN_DATE, getDateFormat());

                            //Insert the new row, returning the primary key value of the new row
                            long newRowId = mDatabaseWrite.insert(Mood.MoodEntry.TABLE_NAME, null, values);

                            //send the comment to the historyActivity
                            mCommentArray.add(editText.getText().toString());
                            editText.setText("", TextView.BufferType.EDITABLE);
                            mEditor.putBoolean("manual", true);
                            mEditor.commit();
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

    //Check the database if their is already a Mood set for the current day. If a Mood is present
    //Delete it from the database.
    private void isNotOlderThanAWeek() {
        String sqlString = "SELECT * from moodDB WHERE Date = '" + getDateFormat() + "'";
        Cursor cursor = mDatabaseRead.rawQuery(sqlString, null);
        if (cursor != null && cursor.moveToNext()) {
            getDateFormat();
            String DateInDatabase = cursor.getString(cursor.getColumnIndex("Date"));
            String todayDate = getDateFormat();
            if (todayDate.equals(DateInDatabase)) {
                //delete the line in the database corresponding to the ID
                int moodID = cursor.getInt(cursor.getColumnIndex(Mood.MoodEntry._ID));
                // Define 'where' part of query.
                String selection = Mood.MoodEntry._ID + " = ?";
                // Specify arguments in placeholder order.
                String[] selectionArgs = {String.valueOf(moodID)};
                // Issue SQL statement.
                mDatabaseWrite.delete(Mood.MoodEntry.TABLE_NAME, selection, selectionArgs);
            }
            cursor.close();
        }
    }


    //Remove the whole entry base on is ID if it's older then 7 days
    public ArrayList<Integer> getMoodOlderThan7Days() {
        ArrayList<Integer> moodIds = new ArrayList<>();
        String sqlString = "SELECT * from moodDB";
        Cursor cursor = mDatabaseRead.rawQuery(sqlString, null);

        while (cursor.moveToNext()) {
            String todayDate = getDateFormat();
            getDateFormat();
            String DateInDatabase = cursor.getString(cursor.getColumnIndex("Date"));
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.FRENCH);
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(todayDate));
                Calendar dbCalendar = Calendar.getInstance();
                dbCalendar.setTime(sdf.parse(DateInDatabase));
                if (dbCalendar.get(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH) > 1) {
                    int moodID = cursor.getInt(cursor.getColumnIndex(Mood.MoodEntry._ID));
                    // Define 'where' part of query.
                    String selection = Mood.MoodEntry._ID + " = ?";
                    // Specify arguments in placeholder order.
                    String[] selectionArgs = {String.valueOf(moodID)};
                    // Issue SQL statement.
                    mDatabaseWrite.delete(Mood.MoodEntry.TABLE_NAME, selection, selectionArgs);
                }
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                break;
            }
        }
        cursor.close();


        return moodIds;
    }

    //Mood Image variable handle with a Switch Case for all 5 different mood
    private int changeMood(int currentMood) {
        if (currentMood < 0) {
            currentMood = 0;
        } else if (currentMood > 4) {
            currentMood = 4;
        }
        mCurrentMood = currentMood;
        //Save the current selected mood in the SharedPreferences
        mEditor = mSharedPreferences.edit();
        mEditor.putInt("mood", currentMood);
        mEditor.commit();
        mMood.setMoodIndex(currentMood);
        float LEFT_VOLUME = 1.0f;
        float RIGHT_VOLUME = 1.0f;
        int NO_LOOP = 0;
        int PRIORITY = 0;
        float NORMAL_PLAY_RATE = 1.0f;
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

    //small variable to generate the date format
    public static final String DATE_FORMAT = "dd-MM-yyyy";

    // Variable to convert simpleDateFormat from Date to String
    public static String getDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.FRENCH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();
        return dateFormat.format(date);
    }


    //Close the database with the onDestroy method
    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }


    // onTouchEven to handle the swipe view
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return !mDetector.onTouchEvent(event);
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

