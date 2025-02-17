package com.devesh.cricket.service;

import com.devesh.cricket.model.Ball;
import com.devesh.cricket.model.Team;
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

    public void simulateBall(Ball ball, Team batting, StrikePair strikePair, int targetRun){
        int run = simulateRun(strikePair);
        ball.setBatsman(strikePair.playerOnStrike);
        handleRunOrWicket(ball, run, batting, strikePair, targetRun);
        gameLogicService.rotateStrike(run, strikePair);
        ballRepository.save(ball);
    }

    public int simulateRun(StrikePair strikePair) {
        return (strikePair.playerOnStrike.getPlayerRole() == PlayerRole.BATTER)
                ? gameLogicService.getRandomBatterWeightScore()
                : gameLogicService.getRandomBowlerWeightScore();
    }

    public void handleRunOrWicket(Ball ball, int run, Team batting, StrikePair strikePair, int targetRun) {
        if (run == -1) {
            handleWicket(ball, batting, strikePair, targetRun);
        } else {
            handleRun(ball, run, batting, strikePair);
        }
    }

    public void handleWicket(Ball ball, Team batting, StrikePair strikePair, int targetRun) {
        batting.incrementWickets();
        ball.setRunsScored(0);
        ball.setWicket(true);
        if (!gameLogicService.gameEnd(batting, targetRun)) {
            strikePair.playerOnStrike = batting.getPlayers().get(strikePair.getNextBatsman());
            strikePair.nextBatsman();
        }
    }

    public void handleRun(Ball ball, int run, Team batting, StrikePair strikePair) {
        batting.addRuns(run);
        strikePair.playerOnStrike.addRuns(run);
        ball.setRunsScored(run);
    }
}
