package com.devesh.cricket.controller;

import com.devesh.cricket.dto.ScoreboardDTO;
import com.devesh.cricket.dto.TeamScoreboardDTO;
import com.devesh.cricket.service.queryService.ScoreboardQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis/scoreboard")
@RequiredArgsConstructor
public class ScoreboardController {

    private final ScoreboardQueryService scoreboardQueryService;

    @GetMapping("/match/{matchId}")
    public ResponseEntity<?> getMatchScoreboard(@PathVariable("matchId") Long matchId) {
        try{
            ScoreboardDTO scoreboardDTO = scoreboardQueryService.findScoreboardByMatchId(matchId);
            if(scoreboardDTO == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(scoreboardDTO);
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/team/{teamId}/match/{matchId}")
    public ResponseEntity<?> getTeamScoreboardByMatchId(@PathVariable("teamId") Long teamId, @PathVariable("matchId") Long matchId) {
        try {
            TeamScoreboardDTO teamScoreboardDTO = scoreboardQueryService.findTeamScoreboardByMatchId(teamId, matchId);
            if(teamScoreboardDTO == null) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .build();
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(teamScoreboardDTO);
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
