package com.devesh.cricket.model;

import com.devesh.cricket.enums.PlayerRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@Entity

public class PlayerMatchStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerMatchStatsId;

    private String playerName;

    @Column(columnDefinition = "int default 0")
    private int runsScored = 0;

    @Column(columnDefinition = "int default 0")
    private int ballsFaced = 0;

    @Column(columnDefinition = "int default 0")
    private int wicketsTaken = 0;

    @Column(columnDefinition = "int default 0")
    private int ballsBowled = 0;

    @Column(columnDefinition = "int default 0")
    private int runsConceded = 0;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
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

    public void incrementWicketTaken() {
        this.wicketsTaken++;
    }

    public void incrementBallsBowled() {
        this.ballsBowled++;
    }

    public void addRunsConceded(int run) {
        this.runsConceded += run;
    }
}
