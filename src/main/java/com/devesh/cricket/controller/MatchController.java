package com.devesh.cricket.controller;


import com.devesh.cricket.dto.StartMatchRequestDTO;
import com.devesh.cricket.model.Match;
import com.devesh.cricket.model.Team;
import com.devesh.cricket.service.queryService.MatchQueryService;
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

    private final MatchQueryService matchQueryService;
    private final MatchService matchService;

    @PostMapping("/start")
    public ResponseEntity<?> startMatch(@RequestBody StartMatchRequestDTO startMatchRequestDTO){
        try {
            Match match = matchService.startMatch(startMatchRequestDTO);
            if (match == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .build();
            }
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(match);
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllMatches(){
        try {
            List<Match> matches = matchQueryService.getAllMatches();
            if (matches == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(matches);
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<?> getMatchById(@PathVariable Long matchId){
        try{
            Match match = matchQueryService.getMatchById(matchId);
            if (match == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(match);
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{matchId}/winner")
    public ResponseEntity<?> getWinnerByMatchId(@PathVariable Long matchId){
        try{
            Team winner = matchQueryService.getWinnerByMatchId(matchId);
            if (winner == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(winner);
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
