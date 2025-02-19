package com.devesh.cricket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class TeamMatchStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamMatchStatsId;

    private String teamName;
    private int totalRuns = 0;
    private int totalWickets = 0;
    private int totalOvers = 0;
    private boolean isWinner;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private Team team;

    @ManyToOne
    @JoinColumn(name = "match_id")
    @JsonIgnore
    private Match match;

    @OneToMany(mappedBy = "teamMatchStats")
    @JsonIgnore
    private List<PlayerMatchStats> players;

    public void addRuns(int runs) {
        this.totalRuns += runs;
    }

    public void incrementWickets() {
        this.totalWickets++;
    }

    public void reset(){
        this.totalRuns = 0;
        this.totalWickets = 0;
        this.totalOvers = 0;
        for(PlayerMatchStats p : players){
            p.setBallsBowled(0);
            p.setBallsFaced(0);
            p.setRunsConceded(0);
            p.setRunsScored(0);
            p.setWicketsTaken(0);
        }
    }
}
