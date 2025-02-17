package com.devesh.cricket.service;

import com.devesh.cricket.config.GameConfig;
import com.devesh.cricket.model.Player;
import com.devesh.cricket.model.Team;
import com.devesh.cricket.ui.UI;
import com.devesh.cricket.utils.GameUtils;
import com.devesh.cricket.utils.StrikePair;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GameLogicService {

    private final GameUtils utils;

    public GameLogicService(UI ui, GameUtils utils) {
        this.utils = utils;
    }

    public boolean gameEnd(Team batting, int targetRun) {
        return (batting.getTotalWickets() == 10 || (targetRun != -1 && batting.getTotalWickets() > targetRun));
    }

    public void swapStrikers(StrikePair strikePair) {
        Player temp = strikePair.playerOnStrike;
        strikePair.playerOnStrike = strikePair.playerOffStrike;
        strikePair.playerOffStrike = temp;
    }

    public void rotateStrike(int run, StrikePair strikePair) {
        if (run % 2 != 0) {
            swapStrikers(strikePair);
        }
    }

    public int getRandomWeightScore(int[] weights) {

        int totalWeight = 0;
        for(int weight : weights) {
            totalWeight += weight;
        }

        Random random = new Random();
        int randomWeight = random.nextInt(totalWeight);

        int cumulativeWeight = 0;
        for(int i = 0; i < weights.length; i++) {
            cumulativeWeight += weights[i];
            if(cumulativeWeight >= randomWeight) {
                return GameConfig.SCORING_OPTIONS[i];
            }
        }
        return GameConfig.SCORING_OPTIONS[0];
    }
    public int getRandomBatterWeightScore() {
        return getRandomWeightScore(GameConfig.BATTER_WEIGHT);
    }

    public int getRandomBowlerWeightScore() {
        return getRandomWeightScore(GameConfig.BOWLER_WEIGHT);
    }

    public int getTotalMatches(int totalTeams) {
        int leagueMatches = (totalTeams) * (totalTeams - 1)/2;
        int knockoutMatches = 3;
        return leagueMatches + knockoutMatches;
    }




///////////////////////////
//    public boolean gameEnd(Team batting, int targetRun) {
//        return (batting.getTotalWickets() == 10 || (targetRun != -1 && batting.getTotalWickets() > targetRun));
//    }
//
//    public int simulateInning(Team team, int targetRun, int overs){
//        StrikePair strikePair = new StrikePair(team.getPlayers().get(0), team.getPlayers().get(1));
//
//        for (int overNo = 1; overNo <= overs && (!gameEnd(team, targetRun)); overNo++) {
//            simulateOver(team, targetRun, overNo, strikePair);
//        }
//
//        return team.getTotalRuns();
//    }
//
//
//    public void simulateOver(Team batting, int targetRun, int overNo, StrikePair strikePair)  {
//        int runThisOver = 0;
//        for (int ball = 1; (ball <= GameConfig.BALLS_PER_OVER) && (!gameEnd(batting, targetRun)); ball++) {
//            runThisOver += processBall(batting, targetRun, strikePair);
//        }
//        swapStrikers(strikePair);
//        ui.displayOverStat(overNo, runThisOver);
//    }
//
//    public int processBall(Team batting, int targetRun, StrikePair strikePair) {
//        int run = simulateRun(strikePair);
//        handleRunOrWicket(run, batting, strikePair, targetRun);
////        strikePair.playerOnStrike.incrementBallsFaced();
//
//        rotateStrike(run, strikePair);
//        ui.displayRunByBall(run);
//        return (run == -1) ? 0 : run;
//    }

//public int inputOvers(UI ui) {
//    return ui.inputOvers();
//}
//
//    public Team toss(Team team1, Team team2) {
//        Toss toss = ui.inputToss();
//        if (toss == Toss.HEADS) {
//            return team1;
//        }
//        return team2;
//    }
//
//
//    public void result(Team battingFirst, Team battingSecond) {
//        int score1 = battingFirst.getTotalRuns();
//        int score2 = battingSecond.getTotalRuns();
//        int won;
//
//        if (score1 > score2) {
//            won = 0;
//            ui.displayMatchResult(battingFirst, battingSecond, won);
//        } else if (score1 < score2) {
//            won = 1;
//            ui.displayMatchResult(battingSecond, battingFirst, won);
//        } else {
//            System.out.println("Match drawn");
//        }
//    }

}
