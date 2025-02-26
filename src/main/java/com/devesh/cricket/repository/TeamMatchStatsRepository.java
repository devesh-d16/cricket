package com.devesh.cricket.repository;

import com.devesh.cricket.entity.Match;
import com.devesh.cricket.entity.TeamMatchStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMatchStatsRepository extends JpaRepository<TeamMatchStats, Long> {
    TeamMatchStats getAllById(Long id);
}
