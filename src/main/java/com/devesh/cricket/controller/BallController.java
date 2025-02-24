package com.devesh.cricket.controller;

import com.devesh.cricket.model.Ball;
import com.devesh.cricket.service.queryService.BallQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apis/balls")
@RequiredArgsConstructor
public class BallController {
    private final BallQueryService ballQueryService;

    @GetMapping
    public ResponseEntity<?> getAllBalls(){
        try {
            List<Ball> balls = ballQueryService.getAllBall();
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
            Ball ball = ballQueryService.getBallById(ballId);
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
            List<Ball> balls = ballQueryService.getBallByOverId(overId);
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

