package com.devesh.cricket.controller;

import com.devesh.cricket.model.Inning;
import com.devesh.cricket.service.InningService;
import com.devesh.cricket.service.TeamService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apis/innings")
public class InningController {

    private final TeamService teamService;
    private final InningService inningService;

    public InningController(TeamService teamService, InningService inningService) {
        this.teamService = teamService;
        this.inningService = inningService;
    }

    @GetMapping
    public ResponseEntity<List<Inning>> getAllInnings(){
        return new ResponseEntity<>(inningService.getAllInnings(), HttpStatus.OK);
    }

    @GetMapping("/{inningId}")
    public ResponseEntity<Inning> getInningById(@PathVariable Long inningId){
        return new ResponseEntity<>(inningService.getInningById(inningId), HttpStatus.OK);
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<Inning>> getInningByMatchId(@PathVariable Long matchId){
        return new ResponseEntity<>(inningService.getInningsByMatch(matchId), HttpStatus.OK);
    }
}
