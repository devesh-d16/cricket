package com.devesh.cricket.service;

import com.devesh.cricket.config.GameConfig;
import com.devesh.cricket.model.*;
import com.devesh.cricket.utils.GameUtils;
import com.devesh.cricket.utils.StrikePair;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.IntStream;

@Service
public class GameLogicService {

    private final GameUtils utils;

    public GameLogicService(GameUtils utils, GameUtils utils1) {
        this.utils = utils1;
    }

    public boolean gameEnd(TeamMatchStats batting, Inning inning, int targetRun) {
        return (inning.getTotalWickets() >= 10 || (targetRun != -1 && batting.getTotalRuns() > targetRun));
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

    public int getRandomWeightScore(int[] weights) {
        int totalWeight = IntStream.of(weights).sum();

        int randomWeight = new Random().nextInt(totalWeight);
        int cumulativeWeight = 0;

        for (int i = 0; i < weights.length; i++) {
            cumulativeWeight += weights[i];
            if (randomWeight < cumulativeWeight) {
                return GameConfig.SCORING_OPTIONS[i];
            }
        }
        throw new IllegalStateException("Weight distribution error");
    }

    public int getRandomBatterWeightScore() {
        return getRandomWeightScore(GameConfig.BATTER_WEIGHT);
    }

    public int getRandomBowlerWeightScore() {
        return getRandomWeightScore(GameConfig.BOWLER_WEIGHT);
    }

    public int getTotalMatches(int totalTeams) {
        int leagueMatches = (totalTeams) * (totalTeams - 1) / 2;
        int knockoutMatches = 3;
        return leagueMatches + knockoutMatches;
    }
}


