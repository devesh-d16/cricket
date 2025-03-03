package com.devesh.cricket.service;

import com.devesh.cricket.entitySql.Player;
import com.devesh.cricket.entitySql.PlayerStats;
import com.devesh.cricket.enums.PlayerRole;
import com.devesh.cricket.exceptions.DuplicateResourceFoundException;
import com.devesh.cricket.exceptions.InvalidRequestException;
import com.devesh.cricket.exceptions.ResourceNotFoundException;
import com.devesh.cricket.exceptions.SystemException;
import com.devesh.cricket.repositorySql.PlayerSqlRepository;
import com.devesh.cricket.repositorySql.TeamSqlRepository;
import lombok.*;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerSqlRepository playerRepository;

    public Player createPlayer(Player player) {

        if (player.getPlayerName() == null || player.getPlayerRole() == null) {
            throw new InvalidRequestException("Player name and role are required.");
        }
        if (playerRepository.existsByPlayerName(player.getPlayerName())) {
            throw new DuplicateResourceFoundException("Player with name '" + player.getPlayerName() + "' already exists.");
        }

        try {
            Player newPlayer = new Player();
            newPlayer.setPlayerRole(player.getPlayerRole());
            newPlayer.setPlayerName(player.getPlayerName());
            return playerRepository.save(newPlayer);
        }
        catch (DataAccessException e) {
            throw new SystemException("Error while creating player '" + player.getPlayerName() + "'. Please try again.");
        }
        catch (Exception e) {
            throw new SystemException("Unexpected error occurred. Please contact support.");
        }
    }

    public List<Player> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        if(players.isEmpty()){
            return Collections.emptyList();
        }
        return players;
    }

    public Player getPlayerById(Long playerId) {
        return playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException("Player with id " + playerId + " not found."));
    }

    public Player getPlayerByName(String name) {
        Player player = playerRepository.getPlayerSqlByPlayerName(name);
        if(player == null){
            throw new ResourceNotFoundException("Player with name " + name + " not found.");
        }
        return player;
    }

    public List<Player> getPlayerByRole(String playerRole) {
        try {
            PlayerRole role = PlayerRole.valueOf(playerRole.toUpperCase());
            return playerRepository.findAllByPlayerRole(role);
        }
        catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid player role: " + playerRole);
        }
    }

    public Player updatePlayer(Long playerId, Player player) {
        if (player.getPlayerName() == null || player.getPlayerRole() == null) {
            throw new InvalidRequestException("Player name and role are required.");
        }

        Player updatePlayer = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Player with id " + playerId + " not found."));

        if (playerRepository.existsByPlayerName(player.getPlayerName()) && !updatePlayer.getPlayerName().equals(player.getPlayerName())) {
            throw new DuplicateResourceFoundException("Player name '" + player.getPlayerName() + "' already exists.");
        }

        updatePlayer.setPlayerName(player.getPlayerName());
        updatePlayer.setPlayerRole(player.getPlayerRole());
        return playerRepository.save(updatePlayer);
    }


    public String deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
        return "Player with id " + playerId + " deleted successfully";
    }

    public void addPlayers(List<Player> players) {
        for (Player player : players){
            createPlayer(player);
        }
    }
}
