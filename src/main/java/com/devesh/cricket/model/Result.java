package com.devesh.cricket.model;

import com.devesh.cricket.entity.TeamMatchStats;
import lombok.Data;

@Data
public class Result {
    private TeamMatchStats winner;
    private int winningMargin;
    private String winningCondition;
}
