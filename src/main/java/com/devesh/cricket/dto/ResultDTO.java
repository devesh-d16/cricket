package com.devesh.cricket.dto;


import com.devesh.cricket.model.Team;
import lombok.Data;

@Data
public class ResultDTO {
    private Team winningTeam;
    private int winningMargin;
    private String winningCondition;
}
