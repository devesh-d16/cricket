package com.devesh.cricket.service;

import com.devesh.cricket.entity.Player;
import com.devesh.cricket.entity.Team;
import com.devesh.cricket.exceptions.PlayerNotFoundException;
import com.devesh.cricket.exceptions.TeamLimitExceededException;
import com.devesh.cricket.exceptions.TeamNotFoundException;
import com.devesh.cricket.repository.PlayerRepository;
import com.devesh.cricket.repository.TeamRepository;
import com.devesh.cricket.utils.GameUtil;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;


    public Player createPlayer(Long teamId, Player player) {

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team with id " + teamId + " not found."));

        if(team.getPlayers().size() > GameUtil.MAX_PLAYERS){
            throw new TeamLimitExceededException("Team already has 11 players");
        }

        Player newPlayer = new Player();
        newPlayer.setName(player.getName());
        newPlayer.setRole(player.getRole());
        newPlayer.setTeam(team);
        playerRepository.save(newPlayer);

        return newPlayer;
    }

    public Player createPlayer(Player player) {

        Player newPlayer = new Player();
        newPlayer.setName(player.getName());
        newPlayer.setRole(player.getRole());
        playerRepository.save(newPlayer);

        return newPlayer;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long playerId) {
        return playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException("Player with id " + playerId + " not found."));
    }

    public Player updatePlayer(Long playerId, Player player) {
        Player playerToUpdate = playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException("Player with id " + playerId + " not found."));
        if (playerToUpdate == null) {
            throw new IllegalArgumentException("Player not found");
        }
        playerToUpdate.setName(player.getName());
        playerToUpdate.setRole(player.getRole());
        playerRepository.save(playerToUpdate);
        return playerToUpdate;
    }

    public void deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    public List<Player> getPlayersByTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team with id " + teamId + " not found."));
        return team.getPlayers();
    }

    public List<Player> addPlayersToTeam(Long teamId, List<Player> players) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Team with id " + teamId + " not found."));
        for (Player player : players) {
            player.setTeam(team);
        }
        List<Player> savedPlayers = playerRepository.saveAll(players);
        team.getPlayers().addAll(savedPlayers);
        teamRepository.save(team);

        return savedPlayers;
    }
}
