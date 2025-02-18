package com.devesh.cricket.utils;

import com.devesh.cricket.model.Player;
import lombok.*;

@Getter
@Setter
public class StrikePair {
    private Player playerOnStrike;
    private Player playerOffStrike;
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
