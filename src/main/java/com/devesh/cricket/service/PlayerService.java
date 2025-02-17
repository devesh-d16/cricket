package com.devesh.cricket.service;

import com.devesh.cricket.model.Player;
import com.devesh.cricket.model.Team;
import com.devesh.cricket.repository.PlayerRepository;
import com.devesh.cricket.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public Player createPlayer(Long teamId, Player player) {

        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new IllegalArgumentException("Team not found")
        );

        if(team.getPlayers().size() >= 11){
            throw new IllegalArgumentException("Team already has 11 players");
        }

        Player newPlayer = new Player();
        newPlayer.setPlayerName(player.getPlayerName());
        newPlayer.setPlayerRole(player.getPlayerRole());
        newPlayer.setTeam(team);
        playerRepository.save(newPlayer);

        return newPlayer;
    }

    public Player createPlayer(Player player) {

        Player newPlayer = new Player();
        newPlayer.setPlayerName(player.getPlayerName());
        newPlayer.setPlayerRole(player.getPlayerRole());
        playerRepository.save(newPlayer);

        return newPlayer;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long playerId) {
        return playerRepository.findById(playerId).orElse(null);
    }

    public Player updatePlayer(Long playerId, Player player) {
        Player playerToUpdate = playerRepository.findById(playerId).orElse(null);
        if (playerToUpdate == null) {
            throw new IllegalArgumentException("Player not found");
        }
        playerToUpdate.setPlayerName(player.getPlayerName());
        playerToUpdate.setPlayerRole(player.getPlayerRole());
        playerRepository.save(playerToUpdate);
        return playerToUpdate;
    }

    public void deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    public List<Player> getPlayersByTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(null);
        if(team != null){
            return team.getPlayers();
        }
        return null;
    }
}
