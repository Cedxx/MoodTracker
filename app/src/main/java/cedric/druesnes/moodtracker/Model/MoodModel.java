package cedric.druesnes.moodtracker.Model;

public class MoodModel {

    private String comment;
    private int moodIndex;

    public MoodModel(){

    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setMoodIndex(int moodIndex) {
        this.moodIndex = moodIndex;
    }

    public String getComment() {
        return comment;
    }

    public int getMoodIndex() {
        return moodIndex;
    }
}
