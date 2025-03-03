package com.devesh.cricket.service;
import com.devesh.cricket.model.StrikePair;
import com.devesh.cricket.utils.GameUtil;
import com.devesh.cricket.entitySql.*;
import com.devesh.cricket.repositorySql.BallSqlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OverService {

    private final BallService ballService;
    private final GameRulesService gameRulesService;
    private final BallSqlRepository ballRepository;


    public void simulateOver(Inning inning, Over over, TeamStats batting, int targetRun, StrikePair strikePair, PlayerStats bowler) {
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

    private Ball createBall(Over over, int ballNumber, PlayerStats bowler) {
        Ball ball = new Ball();
        ball.setOvers(over);
        ball.setBallNumber(ballNumber);
        ball.setBowler(bowler);
        return ball;
    }

    private void handleWicket(Ball ball, Over over, PlayerStats bowler) {
        if (ball.isWicket()) {
            over.addWicket();
            bowler.incrementWicketTaken();
        }
    }

}