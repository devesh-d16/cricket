package com.devesh.cricket.dto;
import lombok.*;

@Data
public class MatchResponseDTO {
    private String title;
    private String venue;
    private int overs;
    private String result;
    TeamResponseDTO team1;
    TeamResponseDTO team2;
}
