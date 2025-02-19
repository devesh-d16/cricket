package com.devesh.cricket.controller;


import com.devesh.cricket.model.Player;
import com.devesh.cricket.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/apis/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/{teamId}/players")
    public Player createPlayer(@PathVariable Long teamId, @RequestBody Player player) {
        return playerService.createPlayer(teamId, player);
    }

    @PostMapping("/{teamId}")
    public ResponseEntity<List<Player>> addPlayersToTeam(@PathVariable Long teamId, @RequestBody List<Player> players) {
        return new ResponseEntity<>(playerService.addPlayersToTeam(teamId, players), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long playerId) {
        return new ResponseEntity<>(playerService.getPlayerById(playerId), HttpStatus.OK);
    }

    @PutMapping("/{playerId}")
    public Player updatePlayer(@PathVariable Long playerId, @RequestBody Player player) {
        return playerService.updatePlayer(playerId, player);
    }

    @DeleteMapping("/{playerId}")
    public void deletePlayer(@PathVariable Long playerId) {
        playerService.deletePlayer(playerId);
    }

    @GetMapping("/{teamId}/players")
    public List<Player> getPlayersByTeam(@PathVariable Long teamId) {
        return playerService.getPlayersByTeam(teamId);
    }
}
