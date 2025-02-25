package com.devesh.cricket.service;

import com.devesh.cricket.entity.Ball;
import com.devesh.cricket.entity.Inning;
import com.devesh.cricket.entity.PlayerMatchStats;
import com.devesh.cricket.entity.TeamMatchStats;
import com.devesh.cricket.enums.PlayerRole;
import com.devesh.cricket.model.StrikePair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BallService {

    private final GameRulesService gameRulesService;
    private final ScoringService scoringService;

    public void simulateBall(Inning inning, Ball ball, TeamMatchStats batting, StrikePair strikePair, int targetRun, PlayerMatchStats bowler){

        PlayerMatchStats playerBatting = strikePair.playerOnStrike;
        playerBatting.incrementBallFaced();
        bowler.incrementBallsBowled();
        ball.setBatsman(playerBatting);

        int run = simulateRun(strikePair);

        handleRunOrWicket(inning, ball, run, batting, strikePair, targetRun, bowler);

        gameRulesService.rotateStrike(run, strikePair);
    }

    public int simulateRun(StrikePair strikePair) {
        return (strikePair.playerOnStrike.getPlayerRole() == PlayerRole.BATTER)
                ? scoringService.getRandomBatterWeightScore()
                : scoringService.getRandomBowlerWeightScore();
    }

    public void handleRunOrWicket(Inning inning, Ball ball, int run, TeamMatchStats batting, StrikePair strikePair, int targetRun, PlayerMatchStats bowler) {
        if (run == -1) {
            handleWicket(inning, ball, batting, strikePair, targetRun);
        } else {
            handleRun(inning, ball, run, batting, strikePair, bowler);
        }
    }

    public void handleWicket(Inning inning, Ball ball, TeamMatchStats batting, StrikePair strikePair, int targetRun) {
        inning.incrementWickets();
        batting.incrementWickets();

        ball.setRuns(0);
        ball.setWicket(true);

        if (!gameRulesService.gameEnd(batting, inning, targetRun) && strikePair.getNextBat() <= 10) {
                strikePair.playerOnStrike = (batting.getPlayers().get(strikePair.getNextBat()));
                strikePair.nextBatsman();
        }
    }

    public void handleRun(Inning inning, Ball ball, int run, TeamMatchStats batting, StrikePair strikePair, PlayerMatchStats bowler) {
        ball.setRuns(run);

        batting.addRuns(run);
        inning.addRuns(run);
        bowler.addRunsConceded(run);

        strikePair.playerOnStrike.addRuns(run);
    }

}
