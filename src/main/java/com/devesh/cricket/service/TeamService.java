package com.devesh.cricket.service;

import com.devesh.cricket.entitySql.Player;
import com.devesh.cricket.entitySql.Team;
import com.devesh.cricket.exceptions.DuplicateResourceFoundException;
import com.devesh.cricket.exceptions.InvalidRequestException;
import com.devesh.cricket.exceptions.ResourceNotFoundException;
import com.devesh.cricket.exceptions.SystemException;
import com.devesh.cricket.repositorySql.TeamSqlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamSqlRepository teamRepository;

    public Team createTeam(Team team) {
        if(team.getTeamName() == null){
            throw new InvalidRequestException("Team name is required");
        }
        try {
            Team newTeam = new Team();
            newTeam.setTeamName(team.getTeamName().toUpperCase());
            return teamRepository.save(team);
        }
        catch (DataAccessException e) {
            throw new SystemException("Error while creating team '" + team.getTeamId() + "'. Please try again.");
        }
        catch (Exception e) {
            throw new SystemException("Unexpected error occurred. Please contact support.");
        }
    }

    public List<Team> getAllTeam() {
        List<Team> teams = teamRepository.findAll();
        if (teams.isEmpty()) {
            return Collections.emptyList();
        }
        return teams;
    }

    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team with id " + teamId + " not found."));
    }

    public Team getTeamByName(String teamName) {
        Team team = teamRepository.findTeamByTeamName(teamName);
        if (team == null) {
            throw new ResourceNotFoundException("Team with name " + teamName + " not found.");
        }
        return team;
    }

    public Team updateTeam(Long teamId, Team team) {
        if(team.getTeamName() == null){
            throw new InvalidRequestException("Team name is required.");
        }
        Team updateTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team with id " + teamId + " not found."));

        if(teamRepository.existsByTeamName(team.getTeamName()) && !updateTeam.getTeamName().equals(team.getTeamName())){
            throw new DuplicateResourceFoundException("Team name '" + team.getTeamName() + "' already exists.");
        }

        updateTeam.setTeamName(team.getTeamName());
        return teamRepository.save(updateTeam);
    }

    public String deleteTeamById(Long teamId) {
        teamRepository.deleteById(teamId);
        return ("Team with id " + teamId + " deleted successfully");
    }


    public void addTeams(List<Team> teams) {
        for (Team team : teams ){
            createTeam(team);
        }
    }
}
