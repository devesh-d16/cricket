package com.devesh.cricket.dao;


import com.devesh.cricket.dto.InningsDTO;
import com.devesh.cricket.dto.MatchUpdateDTO;
import com.devesh.cricket.entitySql.Inning;
import com.devesh.cricket.entitySql.Match;
import com.devesh.cricket.enums.MatchStatus;
import com.devesh.cricket.entitySql.Team;
import com.devesh.cricket.exceptions.GameRuleException;
import com.devesh.cricket.exceptions.ResourceNotFoundException;
import com.devesh.cricket.repositorySql.MatchSqlRepository;
import com.devesh.cricket.repositorySql.PlayerStatsSqlRepository;
import com.devesh.cricket.repositorySql.TeamSqlRepository;
import com.devesh.cricket.service.MatchService;
import com.devesh.cricket.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchDAO {

    private final MatchService matchService;
    private final MatchSqlRepository matchSqlRepository;
    private final TeamSqlRepository teamSqlRepository;
    private final TeamService teamService;


    public List<Match> getAllMatches() {
        List<Match> matches = matchSqlRepository.findAll();
        if(matches.isEmpty()){
            return Collections.emptyList();
        }
        return matches;
    }

    public Match getMatchById(Long matchId) {
       return matchSqlRepository.findById(matchId).orElseThrow(() -> new ResourceNotFoundException("Match with id " + matchId + " not found"));
    }

    public Team getWinnerByMatchId(Long id){
        Match match = matchSqlRepository.getMatchByMatchId(id);
        if(match.getWinner() == null){
            throw new GameRuleException("Match is drawn");
        }
        return match.getWinner().getTeam();
    }

    public List<Match> findAllMatchWonByTeam(Long teamId) {
        Team team = teamSqlRepository.findById(teamId).orElse(null);
        if(team == null) {
            return Collections.emptyList();
        }
        return matchSqlRepository.getMatchesByWinner_Team(team);
    }

    public List<Match> getMatchesByMatchStatus(String matchStatus) {
        try {
            MatchStatus status = MatchStatus.valueOf(matchStatus.toUpperCase());
            return matchSqlRepository.getMatchesByMatchStatus(status);
        } catch (GameRuleException e) {
            throw new GameRuleException("Match status " + matchStatus + " not supported");
        }
    }

    public List<InningsDTO> getMatchScoreboard(Long id) {
        Match match = getMatchById(id);
        List<InningsDTO> inningsDTOs = new ArrayList<>();
        Inning inning1 = match.getInnings().getFirst();
        Inning inning2 = match.getInnings().getLast();
        inningsDTOs.add(matchService.convertToInningsDTO(inning1));
        inningsDTOs.add(matchService.convertToInningsDTO(inning2));
        return inningsDTOs;
    }

    public List<Match> getMatchByTeamId(Long id) {
        Team team = teamService.getTeamById(id);
        return getAllMatches().stream()
                .filter(match -> match.getTeam1().getTeam().equals(team) || match.getTeam2().getTeam().equals(team))
                .collect(Collectors.toList());
    }

    public List<Match> getMatchByTeamPlayedId(Long team1Id, Long team2Id) {
        Team team1 = teamService.getTeamById(team1Id);
        Team team2 = teamService.getTeamById(team2Id);

        return getAllMatches().stream().filter(match -> (match.getTeam1().getTeam().equals(team1) && match.getTeam2().getTeam().equals(team2)) ||
                (match.getTeam1().getTeam().equals(team2) && match.getTeam2().getTeam().equals(team1))).collect(Collectors.toList());
    }

    public List<Match> getMatchByTeamPlayedName(String team1Name, String team2Name) {
        Team team1 = teamService.getTeamByName(team1Name);
        Team team2 = teamService.getTeamByName(team2Name);

        return getAllMatches().stream().filter(match -> (match.getTeam1().getTeam().equals(team1) && match.getTeam2().getTeam().equals(team2)) ||
                (match.getTeam1().getTeam().equals(team2) && match.getTeam2().getTeam().equals(team1))).collect(Collectors.toList());
    }

    public List<Match> getMatchByTeamName(String name) {
        Team team = teamService.getTeamByName(name);
        return getAllMatches().stream()
                .filter(match -> match.getTeam1().getTeam().equals(team) || match.getTeam2().getTeam().equals(team))
                .collect(Collectors.toList());
    }

    public String deleteMatchById(Long id) {
        Match match = getMatchById(id);
        if(match == null){
            return "No match with match id " + id + " found";
        }
        matchSqlRepository.deleteById(id);
        return "Match deleted successfully";
    }

    public Match updateMatch(Long id, MatchUpdateDTO matchUpdateDTO) {
        Match match = getMatchById(id);
        match.setLastUpdate(LocalDateTime.now());
        match.setVenue(matchUpdateDTO.getVenue());
        matchSqlRepository.save(match);
        return match;
    }

    public List<Match> getMatchesByVenue(String venue) {
        return matchSqlRepository.getMatchesByVenue(venue);
    }

    public List<Match> getMatchByWinnerTeamId(Long id) {
        Team team = teamService.getTeamById(id);
        return getAllMatches().stream().filter(match -> match.getWinner().getTeam().equals(team)).collect(Collectors.toList());
    }

    public List<Match> getMatchByWinnerTeamName(String name) {
        Team team = teamService.getTeamByName(name);
        return getAllMatches().stream().filter(match -> match.getWinner().getTeam().equals(team)).collect(Collectors.toList());
    }
}
