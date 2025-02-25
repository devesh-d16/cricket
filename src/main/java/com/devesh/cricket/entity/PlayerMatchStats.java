package com.devesh.cricket.entity;

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
    private Long id;

    private String name;

    @Column(columnDefinition = "int default 0")
    private int runsScored;

    @Column(columnDefinition = "int default 0")
    private int ballsFaced;

    @Column(columnDefinition = "int default 0")
    private int wicketsTaken;

    @Column(columnDefinition = "int default 0")
    private int ballsBowled;

    @Column(columnDefinition = "int default 0")
    private int runsConceded;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PlayerRole playerRole;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
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
