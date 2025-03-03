package com.devesh.cricket.model;

import com.devesh.cricket.entitySql.TeamStats;
import lombok.Data;

@Data
public class Result {
    private TeamStats winner;
    private int winningMargin;
    private String winningCondition;
}
