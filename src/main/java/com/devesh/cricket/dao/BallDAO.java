package com.devesh.cricket.dao;

import com.devesh.cricket.entity.Ball;
import com.devesh.cricket.entity.Over;
import com.devesh.cricket.repository.BallRepository;
import com.devesh.cricket.repository.OverRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BallDAO {
    private final BallRepository ballRepository;
    private final OverRepository overRepository;

    public List<Ball> getAllBall() {
        return ballRepository.findAll();
    }

    public Ball getBallById(Long ballId) {
        return ballRepository.findById(ballId).orElse(null);
    }

    public List<Ball> getBallByOverId(Long overId) {
        Over over = overRepository.findById(overId).orElse(null);
        if (over != null) {
            return over.getBalls();
        }
        return Collections.emptyList();
    }
}
