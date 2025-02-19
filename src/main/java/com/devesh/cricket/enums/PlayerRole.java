package com.devesh.cricket.enums;

import lombok.Getter;

@Getter
public enum PlayerRole {
    BATTER,
    BOWLER;

    public static PlayerRole inputDesignation(String input) {
        return switch (input.toUpperCase()) {
            case "BAT", "BATSMAN", "BATTER" -> BATTER;
            case "BOWL", "BOWLER" -> BOWLER;
            default -> throw new IllegalArgumentException("Invalid input. Must be 'BATTER/BATSMAN/BAT' for Batter or 'BOWL/BOWLER' for Bowler.");
        };
    }
}
