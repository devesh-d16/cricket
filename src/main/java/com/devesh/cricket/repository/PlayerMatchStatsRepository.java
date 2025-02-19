package com.devesh.cricket.repository;

import com.devesh.cricket.model.PlayerMatchStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PlayerMatchStatsRepository extends JpaRepository<PlayerMatchStats, Integer> {

}
