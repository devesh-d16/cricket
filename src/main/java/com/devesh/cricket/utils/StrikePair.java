package com.devesh.cricket.utils;

import com.devesh.cricket.model.Player;

public class StrikePair {
    public Player playerOnStrike;
    public Player playerOffStrike;
    private int nextBat = 2;

    public int getNextBatsman() {
        return nextBat;
    }

    public void nextBatsman() {
        this.nextBat++;
    }

    public StrikePair(Player playerOnStrike, Player playerOffStrike){
        this.playerOnStrike = playerOnStrike;
        this.playerOffStrike = playerOffStrike;
    }
}
