package com.devesh.cricket.model;

import com.devesh.cricket.enums.PlayerRole;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class PlayerMatchStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerMatchStatsId;

    private String playerName;
    private int runsScored = 0;
    private int ballsFaced = 0;
    private int wicketsTaken = 0;
    private int ballsBowled = 0;
    private int runsConceded = 0;

    @Enumerated(EnumType.STRING)
    private PlayerRole playerRole;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "team_stats_id")
    private TeamMatchStats teamMatchStats;

    public void addRuns(int runs) {
        this.runsScored += runs;
    }

    public void incrementBallFaced(){
        this.ballsFaced++;
    }
}
