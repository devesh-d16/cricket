package com.devesh.cricket.service;

import com.devesh.cricket.entity.Inning;
import com.devesh.cricket.entity.PlayerMatchStats;
import com.devesh.cricket.entity.TeamMatchStats;
import com.devesh.cricket.model.StrikePair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameRulesService {

    public boolean gameEnd(TeamMatchStats batting, Inning inning, int targetRun) {
        return (inning.getWickets() >= 10 || (targetRun != -1 && batting.getRuns() > targetRun));
    }

    public void swapStrikers(StrikePair strikePair) {
        PlayerMatchStats temp = strikePair.playerOnStrike;
        strikePair.playerOnStrike = strikePair.playerOffStrike;
        strikePair.playerOffStrike = temp;
    }

    public void rotateStrike(int run, StrikePair strikePair) {
        if (run % 2 != 0) {
            swapStrikers(strikePair);
        }
    }
}