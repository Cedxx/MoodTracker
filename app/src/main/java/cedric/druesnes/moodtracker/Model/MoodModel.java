package cedric.druesnes.moodtracker.Model;

public class MoodModel {

    // Member variable
    private String comment;
    private int moodIndex;
    private String date;

    //Set the Model for the Mood
    public MoodModel() {

    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setMoodIndex(int moodIndex) {
        this.moodIndex = moodIndex;
    }

    public void setDate (String date){
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public int getMoodIndex() {
        return moodIndex;
    }

    public String getDate(){
        return date;
    }
}
