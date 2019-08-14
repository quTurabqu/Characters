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

    private double score = 0;
    private static int lastCardId = -1;
    private GridLayout gridLayout;
    private CardManager cardManager;

    private TextView textView;
    private Intent intent;
    private User user;
    private Button backBtn;
    //private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        // get intent
        intent = getIntent();
        // get current user
        user = ((User)intent.getSerializableExtra("user"));
        // set textToSpeech
//        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if(status != TextToSpeech.ERROR) {
//                    textToSpeech.setLanguage(Locale.UK);
//                }
//            }
//        });
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
        for(int i = 0; i < 16; i++){
            Card card = new Card(getBaseContext(), i,1);
            // generate card - do some changes on it
            card = cardManager.generateUseableCard(card);
            Log.d("CARD", card.toString());
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
            Card card = (Card) gridLayout.getChildAt(i);
            Card pairCard = (Card) gridLayout.getChildAt(card.getPairId());
            if(card.getBackImgId() == -1 || pairCard.getBackImgId() == -1) {
                int imageId = CardManager.getImageId(card.getCustomId());
                card.setBackImgId(imageId);
                pairCard.setBackImgId(imageId);
            }
        }

        final MediaPlayer mediaPlayerClick = MediaPlayer.create(this,R.raw.click);
        final MediaPlayer mediaPlayerFound = MediaPlayer.create(this,R.raw.found);
        final MediaPlayer mediaPlayerFinal = MediaPlayer.create(this,R.raw.end);

        for(int i = 0; i < 16; i++){
            final Card card = (Card) gridLayout.getChildAt(i);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(lastCardId == -1){
                        lastCardId = card.getCustomId();
                        Log.d(">>>LAST CARD ID:", "" + lastCardId);
                        // make a sound
                        mediaPlayerClick.start();
                    }
                    else{
                        if(card.getPairId() == lastCardId){
                            Card lastCard = (Card)gridLayout.getChildAt(lastCardId);
                            Log.d(">>>>FOUND: ", "" + card.getCustomId() + ";" + lastCardId);
                            card.setFound(true);
                            lastCard.setFound(true);
                            lastCardId = -1;
                            // set score
                            score += 62.5;
                            // set user score
                            user.setScore(score);
                            // make a sound
                            mediaPlayerFound.start();
                        }
                        else
                        {
                            final Handler handler = new Handler();
                            final Card lastCard = (Card)gridLayout.getChildAt(lastCardId);
                            lastCardId = card.getCustomId();

                            // make a sound
                            mediaPlayerClick.start();

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    card.setBackgroundResource(android.R.drawable.btn_default);
                                    lastCard.setBackgroundResource(android.R.drawable.btn_default);
                                    card.setOpen(false);
                                    lastCard.setOpen(false);

                                    lastCardId = -1;
                                    //set score
                                    score -= 20.5;
                                    // set user score
                                    user.setScore(score);
                                }
                            },400);
                        }
                        Log.d(">>>LAST CARD ID:", "" + lastCardId);
                    }
                    if(!card.isOpen()){
                        card.setBackground(getResources().getDrawable(getDrawableId(card.getBackImgId())));
                        //textToSpeech.speak("" + card.getBackImgId(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else{
                        if(!card.isFound())
                            card.setBackgroundResource(android.R.drawable.btn_default);
                        lastCardId = -1;
                    }
                    // flip button
                    buttonFlip(card);
                    // check if the user is winner
                    if(isWin()){
                        Intent intent = createIntent(GameScoreActivity.class);
                        intent.putExtra("user", user);
                        // make a sound
                        mediaPlayerFinal.start();
                        startActivity(intent);
                    }
                }
            });
        }

    }

    // helper methods
    private int getDrawableId(int id){
        if(id == 0)
            return R.drawable.firsts;
        if(id == 1)
            return R.drawable.seconds;
        if(id == 2)
            return R.drawable.thirds;
        if(id == 3)
            return R.drawable.fourths;
        if(id == 4)
            return R.drawable.fifths;
        if(id == 5)
            return R.drawable.sixths;
        if(id == 6)
            return R.drawable.sevenths;

        return R.drawable.eights;
    }

    private void buttonFlip(Card card){
        if(card.isOpen()) {
            card.setOpen(false);
        }
        else{
            card.setOpen(true);
        }
    }

    private Intent createIntent(Class<?> t){
        return new Intent(this, t);
    }

    private boolean isWin(){
        for(int i = 0;i < 16; i++){
            Card card = (Card) gridLayout.getChildAt(i);
            if(card.isFound() == false){
                return false;
            }
        }
        return true;
    }

}
