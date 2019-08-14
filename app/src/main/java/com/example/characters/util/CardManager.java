package com.example.characters.util;

import androidx.gridlayout.widget.GridLayout;

import com.example.characters.R;
import com.example.characters.model.Card;

import java.util.Random;

public final class CardManager {

    private static final Random random = new Random();
    private static final int[] PAIRS = new int[16];
    private static final int[] IMAGES = new int[16];
    private static final boolean[] IMAGES_USED = new boolean[8];
    private static final CardManager INSTANCE = new CardManager();

    private CardManager() {
        for(int i = 0; i < PAIRS.length; i++)
            PAIRS[i] = -1;

        for(int i = 0; i < IMAGES.length; i++)
            IMAGES[i] = -1;

        for(int i = 0; i < IMAGES_USED.length; i++)
            IMAGES_USED[i] = false;
    }

    public static CardManager getInstance(){
        return INSTANCE;
    }

    // get pair id for the card
    public static int getPairId(int cardId){
        int num = -1;
        boolean check = true;
        while(check) {
            num = random.nextInt(16);
            if(PAIRS[cardId] != -1)
                return PAIRS[cardId];
            // if PAIRS[cardId] == -1
            if(num != cardId){
                // if pairedId not used before
                if(PAIRS[num] == -1){
                    PAIRS[cardId] = num;
                    PAIRS[num] = cardId;
                    check = false;
                }
            }
        }
        return num;
    }

    // get image id for the card
    public static int getImageId(int cardId){
        int num = -1;
        boolean check = true;
        while(check) {
            num = random.nextInt(8);
            // if imageId found before
            if(IMAGES[cardId] != -1)
                return IMAGES[cardId];
            // if num was not used before
            if(IMAGES_USED[num] == false){
                IMAGES[cardId] = num;
                IMAGES[PAIRS[cardId]] = num;
                IMAGES_USED[num] = true;
                check = false;
            }
        }
        return num;
    }

    // get drawable id of the image id for the card
    public static int getDrawableId(int imageId){
        if(imageId == 0)
            return R.drawable.firsts;
        if(imageId == 1)
            return R.drawable.seconds;
        if(imageId == 2)
            return R.drawable.thirds;
        if(imageId == 3)
            return R.drawable.fourths;
        if(imageId == 4)
            return R.drawable.fifths;
        if(imageId == 5)
            return R.drawable.sixths;
        if(imageId == 6)
            return R.drawable.sevenths;

        return R.drawable.eights;
    }

    // check if all cards open
    public static boolean isWin(GridLayout gridLayout){
        for(int i = 0; i < 16; i++){
            Card card = (Card) gridLayout.getChildAt(i);
            if(card.isFound() == false)
                return false;
        }
        return true;
    }


}
