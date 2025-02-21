package com.devesh.cricket.service;

import com.devesh.cricket.model.Ball;
import com.devesh.cricket.model.Inning;
import com.devesh.cricket.model.PlayerMatchStats;
import com.devesh.cricket.model.TeamMatchStats;
import com.devesh.cricket.enums.PlayerRole;
import com.devesh.cricket.repository.BallRepository;
import com.devesh.cricket.utils.StrikePair;
import org.springframework.stereotype.Service;

@Service
public class BallService {
    private final BallRepository ballRepository;
    private final GameLogicService gameLogicService;

    public BallService(BallRepository ballRepository, GameLogicService gameLogicService) {
        this.ballRepository = ballRepository;
        this.gameLogicService = gameLogicService;
    }

    public void simulateBall(Inning inning, Ball ball, TeamMatchStats batting, StrikePair strikePair, int targetRun, PlayerMatchStats bowler){
        PlayerMatchStats playerBatting = strikePair.playerOnStrike;
        playerBatting.incrementBallFaced();
        bowler.incrementBallsBowled();
        ball.setBatsman(playerBatting);

        int run = simulateRun(strikePair);

        handleRunOrWicket(inning, ball, run, batting, strikePair, targetRun, bowler);

        gameLogicService.rotateStrike(run, strikePair);
        ballRepository.save(ball);
    }

    public int simulateRun(StrikePair strikePair) {
        return (strikePair.playerOnStrike.getPlayerRole() == PlayerRole.BATTER)
                ? gameLogicService.getRandomBatterWeightScore()
                : gameLogicService.getRandomBowlerWeightScore();
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

        ball.setRunsScored(0);
        ball.setWicket(true);

        if (!gameLogicService.gameEnd(batting, inning, targetRun) && strikePair.getNextBat() <= 10) {
                strikePair.playerOnStrike = (batting.getPlayers().get(strikePair.getNextBat()));
                strikePair.nextBatsman();
        }
    }

    public void handleRun(Inning inning, Ball ball, int run, TeamMatchStats batting, StrikePair strikePair, PlayerMatchStats bowler) {
        ball.setRunsScored(run);

        batting.addRuns(run);
        inning.addRuns(run);
        bowler.addRunsConceded(run);

        strikePair.playerOnStrike.addRuns(run);
    }
}
