package com.devesh.cricket.repository;

import com.devesh.cricket.model.Match;
import com.devesh.cricket.model.Player;
import com.devesh.cricket.model.PlayerMatchStats;


import java.util.List;

//@Repository
public interface PlayerMatchStatsRepository{
    List<PlayerMatchStats> findByMatch(Match match);
    List<PlayerMatchStats> findByPlayer(Player player);
}
