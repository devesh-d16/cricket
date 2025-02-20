package com.devesh.cricket.controller;


import com.devesh.cricket.dto.StartMatchRequestDTO;
import com.devesh.cricket.model.Match;
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

    @PostMapping("/start")
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

    @GetMapping("/teams/{teamId}")
    public ResponseEntity<List<Match>> getMatchesByTeamId(@PathVariable Long teamId){
        return new ResponseEntity<>(matchService.getAllMatchesByTeamId(teamId), HttpStatus.OK);
    }
}
