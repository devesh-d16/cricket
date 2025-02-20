package com.devesh.cricket.service;

import com.devesh.cricket.config.GameConfig;
import com.devesh.cricket.model.*;
import com.devesh.cricket.repository.BallRepository;
import com.devesh.cricket.repository.OverRepository;
import com.devesh.cricket.utils.StrikePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OverService {

    private final BallService ballService;
    private final GameLogicService gameLogicService;
    private final OverRepository overRepository;
    private final BallRepository ballRepository;

    public OverService(BallService ballService, GameLogicService gameLogicService, OverRepository overRepository, BallRepository ballRepository) {
        this.ballService = ballService;
        this.gameLogicService = gameLogicService;
        this.overRepository = overRepository;
        this.ballRepository = ballRepository;
    }

    public void simulateOver(Inning inning, Over over, TeamMatchStats batting, int targetRun, StrikePair strikePair, PlayerMatchStats bowler) {
        List<Ball> balls = new ArrayList<>();

        for (int ball = 1; ball <= GameConfig.BALLS_PER_OVER && (!gameLogicService.gameEnd(batting, inning, targetRun)); ball++) {

            Ball newBall = new Ball();
            newBall.setOver(over);
            newBall.setBallNumber(ball);
            newBall.setBowler(bowler);
            ballRepository.save(newBall);

            ballService.simulateBall(inning, newBall, batting, strikePair, targetRun, bowler);

            if (newBall.isWicket()) {
                over.addWicket();
                bowler.incrementWicketTaken();
            }

            over.addRuns(newBall.getRunsScored());
            balls.add(newBall);
        }
        gameLogicService.swapStrikers(strikePair);
        over.setBalls(balls);
        overRepository.save(over);
    }

}
