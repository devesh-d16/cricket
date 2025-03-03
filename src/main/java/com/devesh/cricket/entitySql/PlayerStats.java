package com.devesh.cricket.entitySql;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class PlayerStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerStatId;

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

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private TeamStats teamStats;

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
