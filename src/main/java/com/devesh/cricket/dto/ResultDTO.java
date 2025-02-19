package com.devesh.cricket.dto;


import com.devesh.cricket.model.Team;
import com.devesh.cricket.model.TeamMatchStats;
import lombok.Data;

@Data
public class ResultDTO {
    private TeamMatchStats winningTeam;
    private int winningMargin;
    private String winningCondition;
}
