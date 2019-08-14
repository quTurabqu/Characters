package com.example.characters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.characters.model.User;

public class GameScoreActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private Intent intent;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_score);
        // get intent
        intent = getIntent();
        // text view getter
        scoreTextView = findViewById(R.id.scoreTxt);
        // set score text
        User currentUser = (User)intent.getSerializableExtra("user");
        scoreTextView.setText("SCORE: " + currentUser.getScore());

        // back button
        backBtn = findViewById(R.id.scoreBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }

}
