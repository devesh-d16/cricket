package com.devesh.cricket.service;

import com.devesh.cricket.entity.Player;
import com.devesh.cricket.entity.Team;
import com.devesh.cricket.exceptions.PlayerNotFoundException;
import com.devesh.cricket.exceptions.TeamNotFoundException;
import com.devesh.cricket.repository.PlayerRepository;
import com.devesh.cricket.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public Team createTeam(Team team) {
        Team newTeam = new Team();
        newTeam.setName(team.getName());
        teamRepository.save(team);
        return team;
    }

    public Team addToTeam(Long teamId, Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException("Player with id " + playerId + " not found"));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team with " + teamId + " not found."));
        team.getPlayers().add(player);
        teamRepository.save(team);
        return team;
    }

    public List<Team> getAllTeam() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team with id " + teamId + " not found."));
    }

    public Team getTeamByName(String teamName) {
        return teamRepository.findByName(teamName).orElseThrow(() -> new TeamNotFoundException("Team with name " + teamName + " not found."));
    }
}
