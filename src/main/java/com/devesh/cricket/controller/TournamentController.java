package com.devesh.cricket.controller;


import com.devesh.cricket.model.Tournament;
import com.devesh.cricket.service.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*
API Endpoints I have to make

POST /start -> to start the game or tournament
GET /status -> current status of game
POST /reset -> to restart the game
GET /history -> all past games

*/

@RestController
@RequestMapping("/apis/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PostMapping
    public ResponseEntity<Tournament> startTournament(@RequestBody Tournament tournament) {
        Tournament savedTournament = tournamentService.saveTournament(tournament);
        return new ResponseEntity<>(savedTournament, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        return new ResponseEntity<>(tournamentService.getAllTournaments(), HttpStatus.OK);
    }
    
    @GetMapping("/id/{tournamentId}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable Long tournamentId) {
        return new ResponseEntity<>(tournamentService.getTournamentById(tournamentId), HttpStatus.OK);
    }

    @GetMapping("/name/{tournamentName}")
    public ResponseEntity<Tournament> getTournamentByName(@PathVariable String tournamentName){
        return new ResponseEntity<>(tournamentService.getTournamentByName(tournamentName), HttpStatus.OK);
    }

    @GetMapping("/location/{tournamentLocation}")
    public ResponseEntity<List<Tournament>> getTournamentByLocation(@PathVariable String tournamentLocation){
        return new ResponseEntity<>(tournamentService.getTournamentByLocation(tournamentLocation), HttpStatus.OK);
    }

    @GetMapping("/status/{tournamentStatus}")
    public ResponseEntity<List<Tournament>> getTournamentByStatus(@PathVariable String tournamentStatus){
        return new ResponseEntity<>(tournamentService.getTournamentByStatus(tournamentStatus), HttpStatus.OK);
    }

    @GetMapping("/format/{tournamentFormat}")
    public ResponseEntity<List<Tournament>> getTournamentByFormat(@PathVariable String tournamentFormat){
        return new ResponseEntity<>(tournamentService.getTournamentByFormat(tournamentFormat), HttpStatus.OK);
    }

}


