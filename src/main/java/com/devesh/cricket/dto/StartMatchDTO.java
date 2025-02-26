package com.devesh.cricket.dto;

import lombok.Data;

@Data
public class StartMatchDTO {
    Long team1Id;
    Long team2Id;
    int overs;
}
