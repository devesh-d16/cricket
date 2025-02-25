package com.devesh.cricket.service;

import com.devesh.cricket.model.StrikePair;
import com.devesh.cricket.utils.GameUtil;
import com.devesh.cricket.entity.*;
import com.devesh.cricket.repository.BallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OverService {

    private final BallService ballService;
    private final GameRulesService gameRulesService;
    private final BallRepository ballRepository;

    public void simulateOver(Inning inning, Over over, TeamMatchStats batting, int targetRun, StrikePair strikePair, PlayerMatchStats bowler) {
        List<Ball> balls = new ArrayList<>();

        for (int ballNumber = 1; ballNumber <= GameUtil.BALLS_PER_OVER && !gameRulesService.gameEnd(batting, inning, targetRun); ballNumber++) {
            Ball newBall = createBall(over, ballNumber, bowler);
            ballService.simulateBall(inning, newBall, batting, strikePair, targetRun, bowler);

            handleWicket(newBall, over, bowler);
            over.addRuns(newBall.getRuns());

            ballRepository.save(newBall);
            balls.add(newBall);
        }

        gameRulesService.swapStrikers(strikePair);
        over.setBalls(balls);
    }

    private Ball createBall(Over over, int ballNumber, PlayerMatchStats bowler) {
        Ball ball = new Ball();
        ball.setOver(over);
        ball.setBallNo(ballNumber);
        ball.setBowler(bowler);
        return ball;
    }

    private void handleWicket(Ball ball, Over over, PlayerMatchStats bowler) {
        if (ball.isWicket()) {
            over.addWicket();
            bowler.incrementWicketTaken();
        }
    }

}
