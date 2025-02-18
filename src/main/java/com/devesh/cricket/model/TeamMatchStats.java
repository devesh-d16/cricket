package com.devesh.cricket.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class TeamMatchStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalRuns = 0;
    private int totalWickets = 0;
    private int totalOvers = 0;
    private boolean isWinner;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    public void addRuns(int runs) {
        this.totalRuns += runs;
    }

    public void addWicket() {
        this.totalWickets++;
    }
}
