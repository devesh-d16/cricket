package com.devesh.cricket.model;

import com.devesh.cricket.model.enums.PlayerRole;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@RequiredArgsConstructor
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

    @Enumerated(EnumType.STRING)
    private PlayerRole playerRole;

    @ManyToOne
    @JoinColumn(
            name = "player_id"
    )
    private Player player;

    @ManyToOne
    @JoinColumn(
            name = "match_id"
    )
    private Match match;

    @ManyToOne
    @JoinColumn(
            name = "team_id"
    )
    private TeamMatchStats teamMatchStats;

    public void addRuns(int runs) {
        this.runsScored += runs;
    }

    public void addBallFaced() {
        this.ballsFaced++;
    }

}
