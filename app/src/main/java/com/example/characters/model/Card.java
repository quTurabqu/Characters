package com.example.characters.model;

import android.content.Context;
import android.widget.Button;


public class Card extends Button {

    private int customId;
    private int pairId;
    private int backImgId;
    private boolean isOpen;
    private boolean found;

    private Context myContext;

    public Card(Context context, int customId, int pairId) {
        super(context);
        this.customId = customId;
        this.pairId = pairId;
        this.backImgId = -1;
        this.isOpen = false;
        this.setHeight(250);
        this.myContext = context;
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

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getCustomId() {
        return customId;
    }

    public Context getMyContext(){
        return myContext;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    @Override
    public String toString() {
        return "Card{" +
                "customId=" + customId +
                ", pairId=" + pairId +
                ", backImgId=" + backImgId +
                ", isOpen=" + isOpen +
                '}';
    }
}
