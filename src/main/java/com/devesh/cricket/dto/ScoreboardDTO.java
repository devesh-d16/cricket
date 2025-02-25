package com.devesh.cricket.dto;


import com.devesh.cricket.entity.TeamMatchStats;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ScoreboardDTO {
    private Long matchId;
    private TeamMatchStats team1;
    private TeamMatchStats team2;
    private String winner;
    private String winningMargin;
}
