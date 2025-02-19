package com.devesh.cricket.service;

import com.devesh.cricket.model.Player;
import com.devesh.cricket.model.Team;
import com.devesh.cricket.repository.PlayerRepository;
import com.devesh.cricket.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public Team createTeam(Team team) {
        Team newTeam = new Team();
        newTeam.setTeamName(team.getTeamName());
        teamRepository.save(team);
        return team;
    }

    public Team addToTeam(Long teamId, Long playerId) {
        Player player = playerRepository.findById(playerId).orElse(null);
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team == null) {
            team = new Team();
        }
        team.getPlayers().add(player);
        return teamRepository.save(team);
    }

    public List<Team> getAllTeam() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId).orElse(null);
    }

    public Team getTeamByName(String teamName) {
        return teamRepository.findByTeamName(teamName);
    }

}
