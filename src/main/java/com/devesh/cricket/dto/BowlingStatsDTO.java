package com.devesh.cricket.dto;

import lombok.Data;

@Data
public class BowlingStatsDTO {
    private String name;
    private int oversBowled;
    private int runsConceded;
    private int wicketsTaken;
}
