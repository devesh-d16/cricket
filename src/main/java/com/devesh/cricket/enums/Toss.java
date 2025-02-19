package com.devesh.cricket.enums;

import lombok.*;

@Getter
public enum Toss {
    HEADS, TAILS;

    public static Toss inputToss(String input) {
        return switch (input.toUpperCase()) {
            case "H", "HEAD", "HEADS" -> HEADS;
            case "T", "TAIL", "TAILS" -> TAILS;
            default -> throw new IllegalArgumentException("Invalid input. Must be 'H' for Heads or 'T' for Tails.");
        };
    }
}



