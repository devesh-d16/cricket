package com.devesh.cricket.dto;


import lombok.Data;

import java.util.List;

@Data
public class ScoreboardDTO {
    private List<BattingStatsDTO> battersStats;
    private List<BowlingStatsDTO> bowlerStats;
}
