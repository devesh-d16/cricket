package com.devesh.cricket.dao;


import com.devesh.cricket.enums.MatchStatus;
import com.devesh.cricket.entity.Match;
import com.devesh.cricket.entity.Team;
import com.devesh.cricket.entity.TeamMatchStats;
import com.devesh.cricket.repository.MatchRepository;
import com.devesh.cricket.repository.PlayerMatchStatsRepository;
import com.devesh.cricket.repository.TeamRepository;
import com.devesh.cricket.service.TeamMatchStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchDAO {

    private final TeamMatchStatService teamMatchStatService;
    private final MatchRepository matchRepository;
    private final PlayerMatchStatsRepository playerMatchStatsRepository;
    private final TeamRepository teamRepository;

    public void updateTeamStats(TeamMatchStats teamMatchStats) {
        playerMatchStatsRepository.saveAll(teamMatchStats.getPlayers());
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match getMatchById(Long matchId) {
        return matchRepository.getMatchByMatchId(matchId);
    }

    public List<Match> getAllMatchesByTeamId(Long teamId) {
        return teamMatchStatService.getAllMatchesByTeamId(teamId);
    }

    public Team getWinnerByMatchId(Long matchId) {
        Match match = matchRepository.getMatchByMatchId(matchId);
        return match.getWinner().getTeam();
    }

    public List<Match> getAllCompletedMatches() {
        return matchRepository.getMatchesByMatchStatus(MatchStatus.COMPLETED);
    }

    public List<Match> findAllMatchWonByTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if(team == null) {
            return Collections.emptyList();
        }
        return matchRepository.getMatchesByWinningTeam_Team(team);
    }
}
