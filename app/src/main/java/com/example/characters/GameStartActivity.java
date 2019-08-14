package com.example.characters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.characters.model.Card;
import com.example.characters.model.User;
import com.example.characters.util.CardManager;


@TargetApi(21)
public class GameStartActivity extends AppCompatActivity {

    // media players
    final MediaPlayer mediaPlayerClick = MediaPlayer.create(this, R.raw.click);
    final MediaPlayer mediaPlayerFound = MediaPlayer.create(this, R.raw.found);
    final MediaPlayer mediaPlayerEnd = MediaPlayer.create(this, R.raw.end);
    final MediaPlayer mediaPlayerNotAllowed = MediaPlayer.create(this, R.raw.notallow);

    private double score = 0;
    private static int lastCardId = -1;
    private GridLayout gridLayout;
    private CardManager cardManager;

    private TextView textView;
    private Intent intent;
    private User user;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        // get intent
        intent = getIntent();
        // get current user
        user = ((User)intent.getSerializableExtra("user"));
        // set button listener
        backBtn = findViewById(R.id.button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
        // set user name
        textView = findViewById(R.id.textView);
        textView.setText(textView.getText() + user.getUsername());
        // create CardManager
        cardManager = CardManager.getInstance();
        // set buttons
        gridLayout = findViewById(R.id.gridLayout);
        // add buttons to the grid layout
        for(int i = 0; i < 16; i++){
            int pairId = CardManager.getPairId(i);
            int imageId = CardManager.getImageId(i);
            // create card with given params
            final Card card = new Card(getBaseContext(), i,pairId, imageId);
            Log.d(">>>>CARD:", card.toString());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(5, 0, 0, 0);
            if(i % 4 == 0)
                card.setLayoutParams(params);
            gridLayout.addView(card);
        }

        for(int i = 0; i < 16; i++){
            final Card card = (Card) gridLayout.getChildAt(i);
            // set card listener
            card.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    // last card
                    final Card lastCard = (Card) gridLayout.getChildAt(lastCardId);

                    // *if card found not allow to click
                    if(card.isFound()){
                        mediaPlayerNotAllowed.start(); return;
                    }
                    // not clicked found among un-founds
                    if(lastCardId == -1){
                        card.open();
                        lastCardId = card.getCustomId();
                        // make a sound
                        mediaPlayerClick.start();
                        // update scores
                        score -= 20.5; return;
                    }
                    else{
                        if(card.getPairId() == lastCardId){
                            card.open();
                            lastCardId = -1;
                            // make a sound
                            mediaPlayerFound.start();
                            // set found for lastCard and current clicked card
                            card.setFound(true);
                            lastCard.setFound(true);
                            // update score
                            score += 125;
                        }
                        else{
                            // make a sound
                            mediaPlayerClick.start();
                            card.open();
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    card.close();
                                    lastCard.close();
                                    lastCardId = -1;
                                }
                            },400);
                            // update score
                            score -= 20.5;
                        }
                    }
                    // set score to the current user
                    user.setScore(score);

                    // check if all cards open
                    if(CardManager.isWin(gridLayout)){
                        Intent intent = createIntent(GameScoreActivity.class);
                        mediaPlayerEnd.start();
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private Intent createIntent(Class<?> t){
        Intent intent = new Intent(this, t);
        intent.putExtra("user",  user);
        return intent;
    }
}
