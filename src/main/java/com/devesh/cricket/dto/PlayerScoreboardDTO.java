package com.devesh.cricket.dto;


import com.devesh.cricket.enums.PlayerRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class PlayerScoreboardDTO {
    private String playerName;
    private PlayerRole playerRole;
    private int runsScored;
    private int ballsFaced;
    private int wicketsTaken;
    private int ballsBowled;
    private int runsConceded;
}
