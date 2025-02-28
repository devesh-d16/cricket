package com.devesh.cricket.model;

import com.devesh.cricket.entity.PlayerMatchStats;
import lombok.Getter;

@Getter
public class StrikePair {
    public PlayerMatchStats playerOnStrike;
    public PlayerMatchStats playerOffStrike;
    private int nextBat = 2;


    public void nextBatsman() {
        this.nextBat++;
    }

    public StrikePair(PlayerMatchStats playerOnStrike, PlayerMatchStats playerOffStrike) {
        this.playerOnStrike = playerOnStrike;
        this.playerOffStrike = playerOffStrike;
    }
}
