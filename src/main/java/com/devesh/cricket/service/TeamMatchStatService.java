package com.devesh.cricket.service;

import com.devesh.cricket.model.Match;
import com.devesh.cricket.model.TeamMatchStats;
import com.devesh.cricket.repository.MatchRepository;
import com.devesh.cricket.repository.TeamMatchStatsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamMatchStatService {
    private final TeamMatchStatsRepository teamMatchStatsRepository;
    private final MatchRepository matchRepository;

    public TeamMatchStatService(TeamMatchStatsRepository teamMatchStatsRepository, MatchRepository matchRepository) {
        this.teamMatchStatsRepository = teamMatchStatsRepository;
        this.matchRepository = matchRepository;
    }

    public List<Match> getAllMatchesByTeamId(Long teamId) {
        TeamMatchStats teamMatchStats = teamMatchStatsRepository.getTeamMatchStatsByTeam_TeamId(teamId);
        return new ArrayList<>(matchRepository.getAllByMatchIdIs(teamMatchStats.getMatch().getMatchId()));
    }
}
