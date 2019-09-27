package cedric.druesnes.moodtracker.Model;

import android.provider.BaseColumns;

public final class Mood {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Mood() {
    }

    //create the database entries for each field that we want to save
    public static class MoodEntry implements BaseColumns {
        public static final String TABLE_NAME = "moodDB";
        public static final String COLUMN_COMMENT = "Comment";
        public static final String COLUMN_MOOD_INDEX = "Mood";
        public static final String COLUMN_DATE = "Date";
    }

    //Create the DATABASE
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + MoodEntry.TABLE_NAME + " (" +
            MoodEntry._ID + " INTEGER PRIMARY KEY," +
            MoodEntry.COLUMN_COMMENT + " TEXT," +
            MoodEntry.COLUMN_MOOD_INDEX + " INT," +
            MoodEntry.COLUMN_DATE + "TEXT)";

    //Drop the table is it already exists
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MoodEntry.TABLE_NAME;
}
