package com.devesh.cricket.service;

import com.devesh.cricket.dto.ResultDTO;
import com.devesh.cricket.dto.StartMatchRequestDTO;
import com.devesh.cricket.model.*;
import com.devesh.cricket.model.enums.Status;
import com.devesh.cricket.repository.MatchRepository;

import com.devesh.cricket.repository.PlayerMatchStatsRepository;
import com.devesh.cricket.repository.TeamMatchStatsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final TeamService teamService;
    private final InningService inningService;
    private final ResultService resultService;
    private final MatchRepository matchRepository;
    private final TeamMatchStatsRepository teamMatchStatsRepository;
    private final PlayerMatchStatsRepository playerMatchStatsRepository;

    public MatchService(TeamService teamService, InningService inningService, ResultService resultService, MatchRepository matchRepository, TeamMatchStatsRepository teamMatchStatsRepository, PlayerMatchStatsRepository playerMatchStatsRepository) {
        this.teamService = teamService;
        this.inningService = inningService;
        this.resultService = resultService;
        this.matchRepository = matchRepository;
        this.teamMatchStatsRepository = teamMatchStatsRepository;
        this.playerMatchStatsRepository = playerMatchStatsRepository;
    }

    public Match startMatch(StartMatchRequestDTO startMatchRequestDTO) {
        TeamMatchStats team1 = initializeTeamMatchStats(startMatchRequestDTO.getTeam1Id());
        TeamMatchStats team2 = initializeTeamMatchStats(startMatchRequestDTO.getTeam2Id());
        int overs = startMatchRequestDTO.getOvers();

        Match match = createMatch(team1, team2, overs);

        List<Inning> inningList = new ArrayList<>();

        Inning firstInnings = simulateInning(match, team1, team2, -1);
        inningList.add(firstInnings);
        Inning secondInnings = simulateInning(match, team2, team1, firstInnings.getTotalRuns());
        inningList.add(secondInnings);

        match.setInnings(inningList);

        matchResult(match, firstInnings, secondInnings);

        updateTeamStats(team1);
        updateTeamStats(team2);
        return matchRepository.save(match);
    }

    public Inning simulateInning(Match match, TeamMatchStats battingTeam, TeamMatchStats bowlingTeam, int targetRuns) {
        Inning inning = new Inning();
        inning.setMatch(match);
        inningService.startInnings(inning, battingTeam, bowlingTeam, targetRuns);

        battingTeam.setTotalOvers(inning.getTotalOvers());
        battingTeam.setTotalWickets(inning.getTotalWickets());
        return inning;
    }


    public TeamMatchStats initializeTeamMatchStats(Long teamId) {
        Team team = teamService.getTeamById(teamId);
        TeamMatchStats teamMatchStats = new TeamMatchStats();
        teamMatchStats.setTeam(team);
        teamMatchStats.setTeamName(team.getTeamName());

        List<PlayerMatchStats> playerMatchStats = team.getPlayers().stream().map(player -> {
            PlayerMatchStats playerMatch = new PlayerMatchStats();
            playerMatch.setPlayer(player);
            playerMatch.setPlayerName(player.getPlayerName());
            playerMatch.setPlayerRole(player.getPlayerRole());
            return playerMatch;
        }).toList();

        teamMatchStats.setPlayers(playerMatchStats);
        teamMatchStats.reset();

        return teamMatchStats;
    }

    public Match createMatch(TeamMatchStats team1, TeamMatchStats team2, int overs) {
        team1 = teamMatchStatsRepository.save(team1);
        team2 = teamMatchStatsRepository.save(team2);

        Match match = new Match();
        match.setTeam1(team1);
        match.setTeam2(team2);
        match.setOvers(overs);
        match.setMatchStatus(Status.ONGOING);

        team1.setMatch(match);
        team2.setMatch(match);

        match = matchRepository.save(match);

        return match;
    }


    public void matchResult(Match match, Inning firstInnings, Inning secondInnings) {
        match.setCompleted(true);
        match.setMatchStatus(Status.COMPLETED);

        ResultDTO result = resultService.evaluateResult(firstInnings, secondInnings);

        if (result.getWinningTeam() != null) {
            match.setWinningTeam(result.getWinningTeam());
            match.getWinningTeam().setWinner(true);
        }

        match.setWinningMargin(result.getWinningMargin());
        match.setWinningCondition(result.getWinningCondition());
    }


    public void updateTeamStats(TeamMatchStats teamMatchStats) {
        teamMatchStats.addRuns(teamMatchStats.getTotalRuns());
        for(PlayerMatchStats player : teamMatchStats.getPlayers()) {
            playerMatchStatsRepository.save(player);
            player.getPlayer().addRuns(player.getRunsScored());
            player.getPlayer().addBallFaced(player.getBallsFaced());
        }
    }
}



