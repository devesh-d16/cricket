package com.devesh.cricket.controller;

import com.devesh.cricket.entity.Ball;
import com.devesh.cricket.dao.BallDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/apis/balls")
@RequiredArgsConstructor
public class BallController {
    private final BallDAO ballDao;

    @GetMapping
    public ResponseEntity<?> getAllBalls(){
        try {
            List<Ball> balls = ballDao.getAllBall();
            if(balls.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(balls);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }


    @GetMapping("/{ballId}")
    public ResponseEntity<?> getBallById(@PathVariable Long ballId) {
        try {
            Ball ball = ballDao.getBallById(ballId);
            if(ball == null){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(ball);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/over/{overId}")
    public ResponseEntity<?> getBallByOverId(@PathVariable Long overId) {
        try{
            List<Ball> balls = ballDao.getBallByOverId(overId);
            if(balls.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(balls);
        }
        catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}

