package com.devesh.cricket.utils;

import com.devesh.cricket.model.PlayerMatchStats;

public class StrikePair {
    public PlayerMatchStats playerOnStrike;
    public PlayerMatchStats playerOffStrike;
    private int nextBat = 2;

    public int getNextBatsman() {
        return nextBat;
    }

    public void nextBatsman() {
        this.nextBat++;
    }

    public StrikePair(PlayerMatchStats playerOnStrike, PlayerMatchStats playerOffStrike) {
        this.playerOnStrike = playerOnStrike;
        this.playerOffStrike = playerOffStrike;
    }
}
