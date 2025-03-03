package com.devesh.cricket.model;

import com.devesh.cricket.entitySql.PlayerStats;
import lombok.Getter;

@Getter
public class StrikePair {
    public PlayerStats playerOnStrike;
    public PlayerStats playerOffStrike;
    private int nextBat = 2;


    public void nextBatsman() {
        this.nextBat++;
    }

    public StrikePair(PlayerStats playerOnStrike, PlayerStats playerOffStrike) {
        this.playerOnStrike = playerOnStrike;
        this.playerOffStrike = playerOffStrike;
    }
}
