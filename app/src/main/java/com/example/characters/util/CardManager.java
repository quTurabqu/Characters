package com.example.characters.util;

import androidx.gridlayout.widget.GridLayout;

import com.example.characters.model.Card;

import java.util.Random;

public class CardManager {

    private static final Random random = new Random();
    private static final boolean[] CARDS_ID = new boolean[16];
    private static final boolean[] CARD_IMAGE_ID = new boolean[8];
    private static final int[][] PAIR_CARDS = new int[16][16];
    private static final CardManager INSTANCE = new CardManager();

    private CardManager() {
        for(int i = 0; i < CARDS_ID.length; i++)
            CARDS_ID[i] = false;

        for(int i = 0; i < PAIR_CARDS.length; i++){
            for(int j = 0; j < PAIR_CARDS.length; j++){
                // not paired
                PAIR_CARDS[i][j] = 0;
            }
        }
        for(int i = 0; i < CARD_IMAGE_ID.length; i++)
            CARD_IMAGE_ID[i] = false;
    }

    public static CardManager getInstance(){
        return INSTANCE;
    }

    public Card generateUseableCard(Card card){
        int pairId = getPairId(card.getCustomId());
        card.setPairId(pairId);
        return card;
    }

    private int getPairId(int id){
        int num = -1;
        boolean check = true;
        while(check){
            num = random.nextInt(16);
            for(int i = 0; i < PAIR_CARDS[id].length; i++){
                if(PAIR_CARDS[id][i] == 1)
                    return i;
                if(PAIR_CARDS[i][id] == 1)
                    return id;
            }
            for(int i = 0; i < CARDS_ID.length; i++){
                if((num != id) && (CARDS_ID[num] == false)){
                    // make true for used cards id
                    CARDS_ID[id] = true; CARDS_ID[num] = true;
                    PAIR_CARDS[id][num] = 1; PAIR_CARDS[num][id] = 1;
                    return num;
                }
            }
        }
        return num;
    }

    public static int getImageId(int id){
        int num = -1;
        boolean check = true;
        while(check){
            num = random.nextInt(8);
            if(isAllImagesUsed())
                return -1;
            for(int i = 0; i < CARD_IMAGE_ID.length; i++){
                if(CARD_IMAGE_ID[num] == false){
                    CARD_IMAGE_ID[num] = true;
                    return num;
                }
            }
        }
        return num;
    }

    private boolean isAllUsed(){
        for(int i = 0; i < CARDS_ID.length; i++){
            if(CARDS_ID[i] == false){
                return false;
            }
        }
        return true;
    }

    private static boolean isAllImagesUsed(){
        for(int i = 0; i < CARD_IMAGE_ID.length; i++){
            if(CARD_IMAGE_ID[i] == false)
                return false;
        }
        return true;
    }

}
