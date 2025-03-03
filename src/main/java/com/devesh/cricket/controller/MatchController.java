package com.devesh.cricket.controller;


import com.devesh.cricket.config.responseHandlers.ApiResponse;
import com.devesh.cricket.dto.MatchRequestDTO;
import com.devesh.cricket.dao.MatchDAO;
import com.devesh.cricket.dto.MatchUpdateDTO;
import com.devesh.cricket.entitySql.Match;
import com.devesh.cricket.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/apis/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchDAO matchDAO;
    private final MatchService matchService;

    // done
    @PostMapping("/start")
    public ResponseEntity<?> startMatch(@RequestBody MatchRequestDTO matchRequestDTO){
       return new ResponseEntity<>(matchService.startMatch(matchRequestDTO), HttpStatus.CREATED);
    }

    // done
    @GetMapping
    public ResponseEntity<?> getAllMatches(){
        List<Match> matches = matchDAO.getAllMatches();
        return new ResponseEntity<>(matchService.convertToList(matches), HttpStatus.OK);
    }

    // done
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getMatchById(@PathVariable Long id){
        Match match = matchDAO.getMatchById(id);
        return new ResponseEntity<>( matchService.getMatchResponse(match), HttpStatus.OK);
    }

    // done
    @GetMapping("/{id}/winner")
    public ResponseEntity<?> getWinnerByMatchId(@PathVariable Long id){
        return new ResponseEntity<>(matchDAO.getWinnerByMatchId(id), HttpStatus.OK);
    }

    // done
    @GetMapping("/status")
    public ResponseEntity<?> getCompletedMatches(@RequestParam String matchStatus){
        List<Match> matches = matchDAO.getMatchesByMatchStatus(matchStatus);
        return new ResponseEntity<>(matchService.convertToList(matches), HttpStatus.OK);
    }

    // done
    @GetMapping("/id/{id}/scoreboard")
    public ResponseEntity<?> getMatchScoreboard(@PathVariable Long id){
        return new ResponseEntity<>(matchDAO.getMatchScoreboard(id), HttpStatus.OK);
    }

    // done
    @GetMapping("/teams/id/{id}")
    public ResponseEntity<?> getMatchByTeamId(@PathVariable Long id){
        List<Match> matchList = matchDAO.getMatchByTeamId(id);
        return new ResponseEntity<>(matchService.convertToList(matchList), HttpStatus.OK);
    }

    // done
    @GetMapping("/teams/name/{name}")
    public ResponseEntity<?> getMatchByTeamId(@PathVariable String name){
        List<Match> matchList = matchDAO.getMatchByTeamName(name);
        return new ResponseEntity<>(matchService.convertToList(matchList), HttpStatus.OK);
    }

    // done
    @GetMapping("/teams")
    public ResponseEntity<?> getMatchByTeamPlayed(@RequestParam Long team1Id, @RequestParam Long team2Id){
        List<Match> matches = matchDAO.getMatchByTeamPlayedId(team1Id, team2Id);
        return new ResponseEntity<>(matchService.convertToList(matches), HttpStatus.OK);
    }

    // done
    @GetMapping("/teams/name")
    public ResponseEntity<?> getMatchByTeamPlayed(@RequestParam String team1Name, @RequestParam String team2Name){
        List<Match> matches = matchDAO.getMatchByTeamPlayedName(team1Name, team2Name);
        return new ResponseEntity<>(matchService.convertToList(matches), HttpStatus.OK);
    }

    // done
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateMatch(@RequestBody MatchUpdateDTO matchUpdateDTO, @PathVariable Long id){
        return new ResponseEntity<>(matchDAO.updateMatch(id, matchUpdateDTO), HttpStatus.OK);
    }

    // done
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteMatchById(@PathVariable Long id){
        matchDAO.deleteMatchById(id);
        return ResponseEntity.noContent().build();
    }
}
