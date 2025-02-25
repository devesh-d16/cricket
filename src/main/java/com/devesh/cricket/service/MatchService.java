package com.devesh.cricket.service;

import com.devesh.cricket.dto.StartMatchRequestDTO;
import com.devesh.cricket.enums.PlayerRole;
import com.devesh.cricket.entity.*;
import com.devesh.cricket.enums.MatchStatus;
import com.devesh.cricket.model.Result;
import com.devesh.cricket.repository.InningRepository;
import com.devesh.cricket.repository.MatchRepository;

import com.devesh.cricket.repository.PlayerMatchStatsRepository;
import com.devesh.cricket.repository.TeamMatchStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final TeamService teamService;
    private final InningService inningService;
    private final ResultService resultService;
    private final TeamMatchStatService teamMatchStatService;
    private final MatchRepository matchRepository;
    private final InningRepository inningRepository;
    private final TeamMatchStatsRepository teamMatchStatsRepository;
    private final PlayerMatchStatsRepository playerMatchStatsRepository;

    public Match startMatch(StartMatchRequestDTO startMatchRequestDTO) {
        TeamMatchStats team1 = initializeTeamMatchStats(startMatchRequestDTO.getTeam1Id());
        TeamMatchStats team2 = initializeTeamMatchStats(startMatchRequestDTO.getTeam2Id());

        int overs = startMatchRequestDTO.getOvers();

        Match match = createMatch(team1, team2, overs);
        match.setMatchStatus(MatchStatus.ONGOING);

        List<Inning> inningList =new ArrayList<>();
        Inning firstInnings = simulateInning(match, team1, team2, -1);
        inningList.add(firstInnings);
        Inning secondInnings = simulateInning(match, team2, team1, firstInnings.getRuns());
        inningList.add(secondInnings);

        match.setInnings(inningList);

        matchResult(match, inningList.getFirst(), inningList.getLast());

        System.out.println(team1.getPlayers());
        System.out.println(team2.getPlayers());

        return matchRepository.save(match);
    }


    public TeamMatchStats initializeTeamMatchStats(Long teamId) {
        Team team = teamService.getTeamById(teamId);
        List<Player> teamPlayers = team.getPlayers();

        TeamMatchStats teamMatchStats = new TeamMatchStats();

        teamMatchStats.setTeam(team);
        teamMatchStats.setName(team.getName());

        teamMatchStats.setPlayers(new ArrayList<>());
        List<PlayerMatchStats> matchPlayers = teamPlayers.stream()
                .map(player -> {
                            PlayerMatchStats matchPlayer = new PlayerMatchStats();
                            matchPlayer.setPlayer(player);
                            matchPlayer.setName(player.getName());
                            matchPlayer.setPlayerRole(player.getRole());
                            matchPlayer.setTeamMatchStats(teamMatchStats);
                            playerMatchStatsRepository.save(matchPlayer);
                            return matchPlayer;
                        }
                ).collect(Collectors.toList());
        teamMatchStats.setPlayers(matchPlayers);

        teamMatchStats.setBowlers(new ArrayList<>());
        List<PlayerMatchStats> bowlers = matchPlayers.stream()
                .filter(playerMatchStats -> (playerMatchStats.getPlayerRole() == PlayerRole.BOWLER)
        ).collect(Collectors.toList());
        teamMatchStats.setBowlers(bowlers);

        teamMatchStats.reset();
        teamMatchStatsRepository.save(teamMatchStats);
        return teamMatchStats;
    }

    public Match createMatch(TeamMatchStats team1, TeamMatchStats team2, int overs) {
        Match match = new Match();
        match.setTeam1(team1);
        match.setTeam2(team2);
        match.setOvers(overs);
        match.setMatchStatus(MatchStatus.UPCOMING);

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

        inningService.startInnings(inning, battingTeam, bowlingTeam, targetRuns);

        battingTeam.setOvers(inning.getOvers());
        battingTeam.setWickets(inning.getWickets());
        inningRepository.save(inning);
        return inning;
    }

    public void matchResult(Match match, Inning firstInnings, Inning secondInnings) {
        match.setCompleted(true);
        match.setMatchStatus(MatchStatus.COMPLETED);

        Result result = resultService.evaluateResult(firstInnings, secondInnings);

        if (result.getWinner() != null) {
            match.setWinner(result.getWinner());
            match.getWinner().setWinner(true);
        }

        match.setWinningMargin(result.getWinningMargin());
        match.setWinningCondition(result.getWinningCondition());
    }

}



