package cedric.druesnes.moodtracker.Model;

import java.util.Date;

public class MoodModel {

    // Member variable
    private String comment;
    private int moodIndex;
    private Date date;

    //Set the Model for the Mood
    public MoodModel() {

    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setMoodIndex(int moodIndex) {
        this.moodIndex = moodIndex;
    }

    public void setDate (Date date){
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public int getMoodIndex() {
        return moodIndex;
    }

    public Date getDate(){
        return date;
    }
}
