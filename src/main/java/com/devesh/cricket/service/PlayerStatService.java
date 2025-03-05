package com.devesh.cricket.service;

import com.devesh.cricket.entity.Player;
import com.devesh.cricket.entity.PlayerStats;
import com.devesh.cricket.entity.TeamStats;
import com.devesh.cricket.repositorySql.PlayerStatsSqlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerStatService {

    private final PlayerStatsSqlRepository playerStatsRepository;

    public PlayerStats createPlayerStats(Player player, TeamStats teamStats) {
        PlayerStats playerStats = new PlayerStats();
        playerStats.setPlayer(player);
        playerStats.setTeamStats(teamStats);
        return playerStatsRepository.save(playerStats);
    }
}
