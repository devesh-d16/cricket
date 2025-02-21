package com.devesh.cricket.repository;

import com.devesh.cricket.model.Ball;
import com.devesh.cricket.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BallRepository extends JpaRepository<Ball, Long> {
}
