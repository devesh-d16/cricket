package com.devesh.cricket.repository;

import com.devesh.cricket.model.TeamMatchStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMatchStatsRepository extends JpaRepository<TeamMatchStats, Integer> {
}
