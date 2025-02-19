package com.devesh.cricket.service;

import com.devesh.cricket.config.GameConfig;
import com.devesh.cricket.model.*;
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

    public OverService(BallService ballService, GameLogicService gameLogicService, OverRepository overRepository) {
        this.ballService = ballService;
        this.gameLogicService = gameLogicService;
        this.overRepository = overRepository;
    }

    public void simulateOver(Inning inning, Over over, TeamMatchStats batting, int targetRun, StrikePair strikePair) {
        List<Ball> balls = new ArrayList<>();
        if (over.getId() == null) {
            overRepository.save(over);
        }

        for (int ballNo = 1; ballNo <= GameConfig.BALLS_PER_OVER && (!gameLogicService.gameEnd(batting, inning, targetRun)); ballNo++) {
            Ball newBall = new Ball();
            newBall.setOverNumber(over.getOverNumber());
            newBall.setOver(over);
            newBall.setBallNumber(ballNo);

            ballService.simulateBall(inning, newBall, batting, strikePair, targetRun);
            if (newBall.isWicket()) {
                over.addWicket();
            }
            over.addRuns(newBall.getRunsScored());
            balls.add(newBall);
        }

        gameLogicService.swapStrikers(strikePair);
        over.setBalls(balls);
        overRepository.save(over);
    }

}
