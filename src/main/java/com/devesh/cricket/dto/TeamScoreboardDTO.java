package com.devesh.cricket.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
public class TeamScoreboardDTO {
    private String teamName;
    private int totalRuns;
    private int totalWickets;
    private List<PlayerScoreboardDTO> players;
}
