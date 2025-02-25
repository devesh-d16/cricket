package com.devesh.cricket.controller;


import com.devesh.cricket.dto.StartMatchRequestDTO;
import com.devesh.cricket.entity.Match;
import com.devesh.cricket.entity.Team;
import com.devesh.cricket.dao.MatchDAO;
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
            List<Match> matches = matchDAO.getAllMatches();
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
            Match match = matchDAO.getMatchById(matchId);
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
            Team winner = matchDAO.getWinnerByMatchId(matchId);
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

    @GetMapping("/status/completed")
    public ResponseEntity<?> getCompletedMatches(){
        try {
            List<Match> matches = matchDAO.getAllCompletedMatches();
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

    @GetMapping("/team/{teamId}/winner")
    public ResponseEntity<?> getAllMatchesWonByTeam(@PathVariable Long teamId){
        try{
            List<Match> matches = matchDAO.findAllMatchWonByTeam(teamId);
            if(matches == null || matches.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body("Team has not won any matches");
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
}
