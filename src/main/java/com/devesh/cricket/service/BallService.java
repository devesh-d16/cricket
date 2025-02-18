package com.devesh.cricket.service;

import com.devesh.cricket.model.Ball;
import com.devesh.cricket.model.Inning;
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

    public void simulateBall(Inning inning, Ball ball, Team batting, StrikePair strikePair, int targetRun){
        int run = simulateRun(strikePair);
        ball.setBatsman(strikePair.getPlayerOnStrike());
        handleRunOrWicket(inning, ball, run, batting, strikePair, targetRun);
        gameLogicService.rotateStrike(run, strikePair);
        ballRepository.save(ball);
    }

    public int simulateRun(StrikePair strikePair) {
        return (strikePair.getPlayerOnStrike().getPlayerRole() == PlayerRole.BATTER)
                ? gameLogicService.getRandomBatterWeightScore()
                : gameLogicService.getRandomBowlerWeightScore();
    }

    public void handleRunOrWicket(Inning inning, Ball ball, int run, Team batting, StrikePair strikePair, int targetRun) {
        if (run == -1) {
            handleWicket(inning, ball, batting, strikePair, targetRun);
        } else {
            handleRun(inning, ball, run, batting, strikePair);
        }
    }

    public void handleWicket(Inning inning, Ball ball, Team batting, StrikePair strikePair, int targetRun) {
        inning.incrementWickets();
        ball.setRunsScored(0);
        ball.setWicket(true);
        if (!gameLogicService.gameEnd(batting, inning, targetRun)) {
            strikePair.setPlayerOnStrike(batting.getPlayers().get(strikePair.getNextBatsman()));
            strikePair.nextBatsman();
        }
    }

    public void handleRun(Inning inning, Ball ball, int run, Team batting, StrikePair strikePair) {
        batting.addRuns(run);
        strikePair.getPlayerOnStrike().addRuns(run);
        ball.setRunsScored(run);
    }
}
