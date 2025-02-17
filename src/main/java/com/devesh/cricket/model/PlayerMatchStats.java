package com.devesh.cricket.model;

import com.devesh.cricket.model.enums.PlayerRole;
import jakarta.persistence.*;
import lombok.*;


@Data
//@Entity
public class PlayerMatchStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int runsScored = 0;
    private int ballsFaced = 0;
    private int wicketsTaken = 0;
    private int ballsBowled = 0;
    private int runsConceded = 0;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @Enumerated(EnumType.STRING)
    private PlayerRole playerRole;

}
