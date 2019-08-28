package cedric.druesnes.moodtracker.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cedric.druesnes.moodtracker.R;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);

        TextView textView = findViewById(R.id.commentMood);
        textView.setText(getIntent().getStringExtra("COMMENT"));
    }
}
