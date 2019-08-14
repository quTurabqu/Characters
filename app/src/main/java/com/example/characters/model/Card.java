package com.example.characters.model;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.widget.Button;

import com.example.characters.util.CardManager;


public class Card extends Button {

    private int customId;
    private int pairId;
    private int backImgId;
    private boolean found;

    public Card(Context context, int customId, int pairId, int imageId) {
        super(context);
        this.customId = customId;
        this.pairId = pairId;
        this.backImgId = imageId;
        this.setHeight(250);
        this.setBackgroundResource(android.R.drawable.btn_default);
        this.found = false;
    }

    public int getPairId() {
        return pairId;
    }

    public void setPairId(int pairId) {
        this.pairId = pairId;
    }

    public int getBackImgId() {
        return backImgId;
    }

    public void setBackImgId(int backImgId) {
        this.backImgId = backImgId;
    }

    public int getCustomId() {
        return customId;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    // open the button
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void open(){
        this.setBackground(getResources().getDrawable(CardManager.getDrawableId(this.backImgId)));
    }

    // close the button
    public void close(){
        this.setBackgroundResource(android.R.drawable.btn_default);
    }

    @Override
    public String toString() {
        return "Card{" +
                "customId=" + customId +
                ", pairId=" + pairId +
                ", backImgId=" + backImgId +
                ", found=" + found +
                '}';
    }
}
