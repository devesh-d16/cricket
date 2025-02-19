package com.devesh.cricket.service;

import com.devesh.cricket.model.Ball;
import com.devesh.cricket.model.Inning;
import com.devesh.cricket.model.TeamMatchStats;
import com.devesh.cricket.model.enums.PlayerRole;
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

    public void simulateBall(Inning inning, Ball ball, TeamMatchStats batting, StrikePair strikePair, int targetRun){
        int run = simulateRun(strikePair);
        ball.setBatsman(strikePair.playerOnStrike.getPlayer());
        handleRunOrWicket(inning, ball, run, batting, strikePair, targetRun);
        gameLogicService.rotateStrike(run, strikePair);
        ballRepository.save(ball);
    }

    public int simulateRun(StrikePair strikePair) {
        return (strikePair.playerOnStrike.getPlayerRole() == PlayerRole.BATTER)
                ? gameLogicService.getRandomBatterWeightScore()
                : gameLogicService.getRandomBowlerWeightScore();
    }

    public void handleRunOrWicket(Inning inning, Ball ball, int run, TeamMatchStats batting, StrikePair strikePair, int targetRun) {
        if (run == -1) {
            handleWicket(inning, ball, batting, strikePair, targetRun);
        } else {
            handleRun(inning, ball, run, batting, strikePair);
        }
    }

    public void handleWicket(Inning inning, Ball ball, TeamMatchStats batting, StrikePair strikePair, int targetRun) {
        inning.incrementWickets();
        batting.addWicket();
        ball.setRunsScored(0);
        ball.setWicket(true);
        strikePair.playerOnStrike.incrementBallFaced();
        if (!gameLogicService.gameEnd(batting, inning, targetRun) && strikePair.getNextBatsman() < 10) {
                strikePair.playerOnStrike = (batting.getPlayers().get(strikePair.getNextBatsman()));
        }
    }

    public void handleRun(Inning inning, Ball ball, int run, TeamMatchStats batting, StrikePair strikePair) {
        batting.addRuns(run);
        strikePair.playerOnStrike.addRuns(run);
        strikePair.playerOnStrike.incrementBallFaced();
        ball.setRunsScored(run);
    }
}
