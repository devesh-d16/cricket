package com.devesh.cricket.service;

import com.devesh.cricket.config.GameConfig;
import com.devesh.cricket.model.*;
import com.devesh.cricket.utils.StrikePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class GameLogicService {


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

    private static final Random random = new Random();
    private static List<Integer> generateWeightedList(int[] weights) {
        List<Integer> weightedList = new ArrayList<>();
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i]; j++) {
                weightedList.add(GameConfig.SCORING_OPTIONS[i]);
            }
        }
        return weightedList;
    }

    private static final List<Integer> BATTER_SCORE_LIST = generateWeightedList(GameConfig.BATTER_WEIGHT);
    private static final List<Integer> BOWLER_SCORE_LIST = generateWeightedList(GameConfig.BOWLER_WEIGHT);

    public int getRandomBatterWeightScore() {
        return BATTER_SCORE_LIST.get(random.nextInt(BATTER_SCORE_LIST.size()));
    }

    public int getRandomBowlerWeightScore() {
        return BOWLER_SCORE_LIST.get(random.nextInt(BOWLER_SCORE_LIST.size()));
    }

    public int getTotalMatches(int totalTeams) {
        int leagueMatches = (totalTeams) * (totalTeams - 1) / 2;
        int knockoutMatches = 3;
        return leagueMatches + knockoutMatches;
    }
}


