package com.devesh.cricket.model;

import com.devesh.cricket.entity.TeamStats;
import lombok.Data;

@Data
public class Result {
    private TeamStats winner;
    private int winningMargin;
    private String winningCondition;
}
