package com.devesh.cricket.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
//@Entity
public class Scoreboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreboardId;

    @OneToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "batting_team_id")
    private Team battingTeam;

    @ManyToOne
    @JoinColumn(name = "bowling_team_id")
    private Team bowlingTeam;

    private int totalRuns = 0;
    private int totalWickets = 0;
    private int totalOvers = 0;

    public void addRuns(int runs) {
        this.totalRuns += runs;
    }

    public void incrementWickets() {
        if (this.totalWickets < 10) {
            this.totalWickets++;
        }
    }

    public void incrementOvers() {
        this.totalOvers++;
    }
}
