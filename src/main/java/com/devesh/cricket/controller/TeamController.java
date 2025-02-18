package com.devesh.cricket.controller;


import com.devesh.cricket.model.Team;
import com.devesh.cricket.service.PlayerService;
import com.devesh.cricket.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/teams")
public class TeamController {

    private final TeamService teamService;
    private final PlayerService playerService;

    public TeamController(TeamService teamService, PlayerService playerService) {
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        return new ResponseEntity<>(teamService.createTeam(team), HttpStatus.CREATED);
    }

    @PostMapping("/{tournamentId}")
    public ResponseEntity<HttpStatus> addTeamToTournament(@RequestBody List<Team> teams, @PathVariable Long tournamentId){
        teamService.addTeamToTournament(teams, tournamentId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        return new ResponseEntity<>(teamService.getAllTeam(), HttpStatus.OK);
    }

    @GetMapping("/id/{teamId}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long teamId) {
        return new ResponseEntity<>(teamService.getTeamById(teamId), HttpStatus.OK);
    }

    @GetMapping("/name/{teamName}")
    public ResponseEntity<Team> getTeamByName(@PathVariable String teamName){
        return new ResponseEntity<>(teamService.getTeamByName(teamName), HttpStatus.OK);
    }

    @GetMapping("/{tournamentId}")
    public ResponseEntity<List<Team>> getTeamByTournamentId(@PathVariable Long tournamentId){
        return new ResponseEntity<>(teamService.getTeamByTournamentId(tournamentId), HttpStatus.OK);
    }
}