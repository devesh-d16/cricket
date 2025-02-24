package com.devesh.cricket.service.queryService;


import com.devesh.cricket.model.Match;
import com.devesh.cricket.model.Team;
import com.devesh.cricket.model.TeamMatchStats;
import com.devesh.cricket.repository.MatchRepository;
import com.devesh.cricket.repository.PlayerMatchStatsRepository;
import com.devesh.cricket.service.TeamMatchStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchQueryService {

    private final TeamMatchStatService teamMatchStatService;
    private final MatchRepository matchRepository;
    private final PlayerMatchStatsRepository playerMatchStatsRepository;

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
        return match.getWinningTeam().getTeam();
    }
}
