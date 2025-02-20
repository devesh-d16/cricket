package com.devesh.cricket.controller;


import com.devesh.cricket.dto.StartMatchRequestDTO;
import com.devesh.cricket.model.Match;
import com.devesh.cricket.model.Team;
import com.devesh.cricket.model.TeamMatchStats;
import com.devesh.cricket.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/apis/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    public ResponseEntity<Match> startMatch(@RequestBody StartMatchRequestDTO startMatchRequestDTO){
        Match match = matchService.startMatch(startMatchRequestDTO);
        return new ResponseEntity<>(match, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches(){
        return new ResponseEntity<>(matchService.getAllMatches(), HttpStatus.OK);
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long matchId){
        return new ResponseEntity<>(matchService.getMatchById(matchId), HttpStatus.OK);
    }

    @GetMapping("/{matchId}/winner")
    public ResponseEntity<Team> getWinnerByMatchId(@PathVariable Long matchId){
        return new ResponseEntity<>(matchService.getWinnerByMatchId(matchId), HttpStatus.OK);
    }
}
