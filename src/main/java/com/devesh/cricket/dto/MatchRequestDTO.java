package com.devesh.cricket.dto;

import lombok.*;

@Data
public class MatchRequestDTO {
    TeamRequestDTO team1;
    TeamRequestDTO team2;
    String venue;
    int overs;
}
