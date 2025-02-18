package com.devesh.cricket.service;

import com.devesh.cricket.dto.ResultDTO;
import com.devesh.cricket.dto.StartMatchRequestDTO;
import com.devesh.cricket.model.*;
import com.devesh.cricket.model.enums.Status;
import com.devesh.cricket.repository.MatchRepository;
import com.devesh.cricket.repository.PlayerMatchStatsRepository;
import com.devesh.cricket.repository.TeamMatchStatsRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("prototype")
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamService teamService;
    private final InningService inningService;
    private final ResultService resultService;
    private final PlayerMatchStats playerMatchStats;
    private final PlayerMatchStatsRepository playerMatchStatsRepository;
    private final TeamMatchStats teamMatchStats;
    private final TeamMatchStatsRepository teamMatchStatsRepository;

    public MatchService(MatchRepository matchRepository, TeamService teamService, InningService inningService, ResultService resultService, PlayerMatchStats playerMatchStats, PlayerMatchStatsRepository playerMatchStatsRepository, TeamMatchStats teamMatchStats, TeamMatchStatsRepository teamMatchStatsRepository) {
        this.matchRepository = matchRepository;
        this.teamService = teamService;
        this.inningService = inningService;
        this.resultService = resultService;
        this.playerMatchStats = playerMatchStats;
        this.playerMatchStatsRepository = playerMatchStatsRepository;
        this.teamMatchStats = teamMatchStats;
        this.teamMatchStatsRepository = teamMatchStatsRepository;
    }


    public Match startMatch(StartMatchRequestDTO startMatchRequestDTO) {
        Team team1 = teamService.getTeamById(startMatchRequestDTO.getTeam1Id());
        Team team2 = teamService.getTeamById(startMatchRequestDTO.getTeam2Id());

        Match match = new Match();
        match.setTeam1(team1);
        match.setTeam2(team2);
        match.setOvers(20);
        match.setMatchStatus(Status.ONGOING);
        match = matchRepository.save(match);

        List<Inning> matchInnings = new ArrayList<>();

        Inning firstInnings = new Inning();
        firstInnings.setMatch(match);
        inningService.startInnings(firstInnings, team1, team2, -1);
        matchInnings.add(firstInnings);

        Inning secondInnings = new Inning();
        secondInnings.setMatch(match);
        inningService.startInnings(secondInnings, team2, team1, firstInnings.getTotalRuns());
        matchInnings.add(secondInnings);

        match.setInnings(matchInnings);
        match.setCompleted(true);
        match.setMatchStatus(Status.COMPLETED);

        ResultDTO result = resultService.evaluateResult(firstInnings, secondInnings);
        match.setWinningTeam(result.getWinningTeam());
        match.setWinningMargin(result.getWinningMargin());
        match.setWinningCondition(result.getWinningCondition());

        matchRepository.save(match);
        return match;
    }
}



