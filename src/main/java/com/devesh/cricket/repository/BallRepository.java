package com.devesh.cricket.repository;

import com.devesh.cricket.entity.Ball;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BallRepository extends JpaRepository<Ball, Long> {
    List<Ball> findAllByBallIdAndOver_OverId(Long ballId, Long overOverId);
}
