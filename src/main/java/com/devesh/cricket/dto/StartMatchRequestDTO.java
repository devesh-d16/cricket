package com.devesh.cricket.dto;

import lombok.Data;

@Data
public class StartMatchRequestDTO {
    Long team1Id;
    Long team2Id;
    int overs;
}
