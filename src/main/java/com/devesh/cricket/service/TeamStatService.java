package com.devesh.cricket.service;

import com.devesh.cricket.entity.PlayerStats;
import com.devesh.cricket.entity.Team;
import com.devesh.cricket.entity.TeamStats;
import com.devesh.cricket.repositorySql.TeamStatsSqlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamStatService {

    private final TeamStatsSqlRepository teamStatsRepository;

    public TeamStats createTeamStats(Team team) {
        TeamStats teamStats = new TeamStats();
        teamStats.setTeam(team);
        teamStats.reset();
        return teamStatsRepository.save(teamStats);
    }

    public void updateTeamPlayers(TeamStats teamStats, List<PlayerStats> players) {
        teamStats.setPlayers(players);
        teamStatsRepository.save(teamStats);
    }
}

