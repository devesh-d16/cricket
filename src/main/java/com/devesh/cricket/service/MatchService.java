package com.devesh.cricket.service;

import com.devesh.cricket.dto.ResultDTO;
import com.devesh.cricket.dto.StartMatchRequestDTO;
import com.devesh.cricket.model.*;
import com.devesh.cricket.enums.Status;
import com.devesh.cricket.repository.InningRepository;
import com.devesh.cricket.repository.MatchRepository;

import com.devesh.cricket.repository.PlayerMatchStatsRepository;
import com.devesh.cricket.repository.TeamMatchStatsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    private final TeamService teamService;
    private final InningService inningService;
    private final ResultService resultService;
    private final TeamMatchStatService teamMatchStatService;
    private final MatchRepository matchRepository;
    private final InningRepository inningRepository;
    private final TeamMatchStatsRepository teamMatchStatsRepository;
    private final PlayerMatchStatsRepository playerMatchStatsRepository;

    public MatchService(TeamService teamService,
                        InningService inningService,
                        ResultService resultService, TeamMatchStatService teamMatchStatService,
                        MatchRepository matchRepository,
                        InningRepository inningRepository,
                        TeamMatchStatsRepository teamMatchStatsRepository,
                        PlayerMatchStatsRepository playerMatchStatsRepository
    ) {
        this.teamService = teamService;
        this.inningService = inningService;
        this.resultService = resultService;
        this.teamMatchStatService = teamMatchStatService;
        this.matchRepository = matchRepository;
        this.inningRepository = inningRepository;
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


    public TeamMatchStats initializeTeamMatchStats(Long teamId) {
        TeamMatchStats teamMatchStats = new TeamMatchStats(); // will store the stats for the match
        Team team = teamService.getTeamById(teamId); // real team class retrieval

        teamMatchStats.setTeam(team); // set stats belong to which team
        teamMatchStats.setTeamName(team.getTeamName()); // store team name

        List<PlayerMatchStats> players = team.getPlayers().stream()
                .map(player ->
                {
                    PlayerMatchStats playerMatch = new PlayerMatchStats();
                    playerMatch.setPlayer(player);
                    playerMatch.setPlayerName(player.getPlayerName());
                    playerMatch.setPlayerRole(player.getPlayerRole());
                    playerMatchStatsRepository.save(playerMatch);
                    return playerMatch;
                }
        ).toList();
        teamMatchStats.setPlayers(players);

        teamMatchStats.reset(); // to reset the stat for each new match
        teamMatchStatsRepository.save(teamMatchStats);
        teamMatchStats.getPlayers().forEach(player -> player.setTeamMatchStats(teamMatchStats));
        return teamMatchStats;
    }


    public Match createMatch(TeamMatchStats team1, TeamMatchStats team2, int overs) {

        Match match = new Match();
        match.setTeam1(team1);
        match.setTeam2(team2);
        match.setOvers(overs);
        match.setMatchStatus(Status.ONGOING);

        team1.setMatch(match);
        team2.setMatch(match);

        matchRepository.save(match);
        return match;
    }


    public Inning simulateInning(Match match, TeamMatchStats battingTeam, TeamMatchStats bowlingTeam, int targetRuns) {

        Inning inning = new Inning();
        inning.setMatch(match);
        inning.setBattingTeam(battingTeam);
        inning.setBowlingTeam(bowlingTeam);
        inningRepository.save(inning);

        inningService.startInnings(inning, battingTeam, bowlingTeam, targetRuns);

        battingTeam.setTotalOvers(inning.getTotalOvers());
        battingTeam.setTotalWickets(inning.getTotalWickets());
        return inning;
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
}



