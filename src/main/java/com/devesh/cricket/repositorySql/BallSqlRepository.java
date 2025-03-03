package com.devesh.cricket.repositorySql;

import com.devesh.cricket.entitySql.Ball;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BallSqlRepository extends JpaRepository<Ball, Long> {
}
