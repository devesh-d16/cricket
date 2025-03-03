package com.devesh.cricket.service;

import com.devesh.cricket.dto.*;
import com.devesh.cricket.entitySql.*;
import com.devesh.cricket.enums.MatchStatus;
import com.devesh.cricket.enums.PlayerRole;
import com.devesh.cricket.model.Result;
import com.devesh.cricket.repositorySql.MatchSqlRepository;
import com.devesh.cricket.repositorySql.PlayerStatsSqlRepository;
import com.devesh.cricket.repositorySql.TeamStatsSqlRepository;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final TeamService teamService;
    private final PlayerService playerService;
    private final InningService inningService;
    private final ResultService resultService;
    private final MatchSqlRepository matchRepository;
    private final TeamStatsSqlRepository teamStatsRepository;
    private final PlayerStatsSqlRepository playerStatsRepository;

    public MatchResponseDTO startMatch(MatchRequestDTO matchRequestDTO) {

        // validate the team information
        validateMatchRequest(matchRequestDTO);

        TeamStats team1 = initializeTeamMatchStats(matchRequestDTO.getTeam1());
        TeamStats team2 = initializeTeamMatchStats(matchRequestDTO.getTeam2());

        // match overs
        int overs = matchRequestDTO.getOvers();

        // creating match object
        Match match = createMatch(team1, team2, overs);
        match.setVenue(matchRequestDTO.getVenue());
        match.setLastUpdate(LocalDateTime.now());
        match.setMatchStatus(MatchStatus.IN_PROGRESS);

        // list to store both innings
        List<Inning> inningList = new ArrayList<>();

        Inning firstInnings = simulateInning(match, team1, team2, -1);  // (targetRuns for first innings = -1)
        inningList.add(firstInnings);

        Inning secondInnings = simulateInning(match, team2, team1, firstInnings.getRuns());
        inningList.add(secondInnings);

        match.setInnings(inningList);

        // To handle match result
        matchResult(match, inningList.getFirst(), inningList.getLast());
        matchRepository.save(match);

        return getMatchResponse(match);
    }


    private void validateMatchRequest(MatchRequestDTO matchRequestDTO) {
        String team1Name = matchRequestDTO.getTeam1().getTeamName();
        String team2Name = matchRequestDTO.getTeam2().getTeamName();

        // Teams are not the same
        if (team1Name.equalsIgnoreCase(team2Name)) {
            throw new IllegalArgumentException("Both teams cannot be the same.");
        }

        // Not have more than 11 players
        if (matchRequestDTO.getTeam1().getPlayers().size() > 11) {
            throw new IllegalArgumentException("Team " + team1Name + " has more than 11 players.");
        }
        if (matchRequestDTO.getTeam2().getPlayers().size() > 11) {
            throw new IllegalArgumentException("Team " + team2Name + " has more than 11 players.");
        }

        // No common players
        Set<String> team1Players = new HashSet<>(matchRequestDTO.getTeam1().getPlayers());
        Set<String> team2Players = new HashSet<>(matchRequestDTO.getTeam2().getPlayers());

        team1Players.retainAll(team2Players); // Finds common players

        if (!team1Players.isEmpty()) {
            throw new IllegalArgumentException("Teams cannot have common players: " + team1Players);
        }
    }


    public TeamStats initializeTeamMatchStats(TeamRequestDTO team) {

        Team teamSql = teamService.getTeamByName(team.getTeamName());
        List<Player> players = new ArrayList<>();
        team.getPlayers().forEach(player -> players.add(playerService.getPlayerByName(player)));


        TeamStats teamStats = new TeamStats();

        teamStats.setTeam(teamSql);

        List<PlayerStats> matchPlayers = players.stream()
                .map(player -> {
                            PlayerStats matchPlayer = new PlayerStats();
                            matchPlayer.setPlayer(player);
                            matchPlayer.setTeamStats(teamStats);
                            return matchPlayer;
                        }
                ).collect(Collectors.toList());
        playerStatsRepository.saveAll(matchPlayers);
        teamStats.setPlayers(new ArrayList<>(matchPlayers));

        List<PlayerStats> bowlers = matchPlayers.stream()
                .filter(playerMatchStats -> (playerMatchStats.getPlayer().getPlayerRole() == PlayerRole.BOWLER)
                ).collect(Collectors.toList());
        teamStats.setBowlers(new ArrayList<>(bowlers));

        teamStats.reset();
        teamStatsRepository.save(teamStats);
        return teamStats;
    }

    public Match createMatch(TeamStats team1, TeamStats team2, int overs) {
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


    public Inning simulateInning(Match match, TeamStats battingTeam, TeamStats bowlingTeam, int targetRuns) {

        Inning inning = new Inning();
        inning.setMatch(match);
        inning.setBattingTeam(battingTeam);
        inning.setBowlingTeam(bowlingTeam);
        inningService.startInnings(inning, battingTeam, bowlingTeam, targetRuns);

        battingTeam.setOvers(inning.getOvers());
        battingTeam.setWickets(inning.getWickets());

        return inning;
    }


    public void matchResult(Match match, Inning firstInnings, Inning secondInnings) {
        match.setMatchStatus(MatchStatus.COMPLETED);

        Result result = resultService.evaluateResult(firstInnings, secondInnings);

        if (result.getWinner() != null) {
            match.setWinner(result.getWinner());
            match.getWinner().setWinner(true);
        }

        match.setWinningCondition(result.getWinningCondition());
    }

    public List<MatchResponseDTO> convertToList(List<Match> matches){
        return matches.stream().map(this::getMatchResponse).collect(Collectors.toList());
    }

    public MatchResponseDTO getMatchResponse(Match match) {
        MatchResponseDTO dto = new MatchResponseDTO();
        dto.setTitle(match.getTeam1().getTeam().getTeamName() + " v/s " + match.getTeam2().getTeam().getTeamName());
        dto.setVenue(match.getVenue());
        dto.setOvers(match.getOvers());
        dto.setResult(match.getWinningCondition());
        dto.setTeam1(convertToTeamDTO(match.getTeam1()));
        dto.setTeam2(convertToTeamDTO(match.getTeam2()));
        return dto;
    }

    public TeamResponseDTO convertToTeamDTO(TeamStats team) {
        if (team == null) return null; // Avoid NullPointerException
        TeamResponseDTO dto = new TeamResponseDTO();
        dto.setTeamName(team.getTeam().getTeamName());
        dto.setRuns(team.getRuns());
        dto.setWickets(team.getWickets());
        dto.setOvers(team.getOvers());
        return dto;
    }

    public InningsDTO convertToInningsDTO(Inning innings){
        InningsDTO inningsDTO = new InningsDTO();
        inningsDTO.setBattingTeam(innings.getBattingTeam().getTeam().getTeamName());
        inningsDTO.setBowlingTeam(innings.getBowlingTeam().getTeam().getTeamName());
        inningsDTO.setRuns(innings.getRuns());
        inningsDTO.setWickets(innings.getWickets());
        inningsDTO.setOvers(innings.getOvers());
        inningsDTO.setScoreboardDTO(convertToScoreboardDTO(innings));
        return inningsDTO;
    }

    public ScoreboardDTO convertToScoreboardDTO(Inning innings) {
        ScoreboardDTO scoreboardDTO = new ScoreboardDTO();
        List<BattingStatsDTO> batterStats = convertToBattingStatsDTO(innings.getBattingTeam());
        List<BowlingStatsDTO> bowlerStats = convertToBowlingStatsDTO(innings.getBowlingTeam());
        scoreboardDTO.setBattersStats(batterStats);
        scoreboardDTO.setBowlerStats(bowlerStats);
        return scoreboardDTO;
    }

    public List<BowlingStatsDTO> convertToBowlingStatsDTO(TeamStats bowlingTeam) {
        List<BowlingStatsDTO> bowl =  new ArrayList<>();
        List<PlayerStats> players = bowlingTeam.getPlayers();
        for(PlayerStats bowler : players){
            if(bowler.getPlayer().getPlayerRole() == PlayerRole.BOWLER) {
                BowlingStatsDTO bowlingStatsDTO = new BowlingStatsDTO();
                bowlingStatsDTO.setName(bowler.getPlayer().getPlayerName());
                bowlingStatsDTO.setOversBowled((bowler.getBallsBowled())/6);
                bowlingStatsDTO.setRunsConceded(bowler.getRunsConceded());
                bowlingStatsDTO.setWicketsTaken(bowler.getWicketsTaken());
                bowl.add(bowlingStatsDTO);
            }
        }
        return bowl;
    }

    public List<BattingStatsDTO> convertToBattingStatsDTO(TeamStats battingTeam) {
        List<PlayerStats> players = battingTeam.getPlayers();
        List<BattingStatsDTO> bat = new ArrayList<>();
        for(PlayerStats batter : players){
            BattingStatsDTO battingStatsDTO = new BattingStatsDTO();
            battingStatsDTO.setName(batter.getPlayer().getPlayerName());
            battingStatsDTO.setRunsScored(batter.getRunsScored());
            battingStatsDTO.setBallsFaced(batter.getBallsFaced());
            bat.add(battingStatsDTO);
        }
        return bat;
    }

}