package com.devesh.cricket.controller;


import com.devesh.cricket.dto.StartMatchRequestDTO;
import com.devesh.cricket.model.Match;
import com.devesh.cricket.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/apis/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/start")
    public ResponseEntity<?> startMatch(@RequestBody StartMatchRequestDTO startMatchRequestDTO){
        Match match = matchService.startMatch(startMatchRequestDTO);
        return new ResponseEntity<>(match, HttpStatus.CREATED);
    }
}
