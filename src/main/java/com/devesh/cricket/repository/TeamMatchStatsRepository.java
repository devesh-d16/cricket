package com.devesh.cricket.repository;

import com.devesh.cricket.model.Match;
import com.devesh.cricket.model.TeamMatchStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMatchStatsRepository extends JpaRepository<TeamMatchStats, Long> {
    TeamMatchStats getTeamMatchStatsByTeamMatchStatsId(Long teamMatchStatsId);

    TeamMatchStats getTeamMatchStatsByTeam_TeamId(Long teamTeamId);

    List<TeamMatchStats> getTeamMatchStatsByMatch(Match match);
}
