package com.devesh.cricket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(columnDefinition = "int default 0")
    private int totalRuns = 0;

    @Column(columnDefinition = "int default 0")
    private int totalWickets = 0;

    @Column(columnDefinition = "int default 0")
    private int totalOvers = 0;

    @Column(columnDefinition = "BIT")
    private boolean isWinner;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private Team team;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id")
    @JsonIgnore
    private Match match;

    @OneToMany(mappedBy = "teamMatchStats", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PlayerMatchStats> players;

    @Transient
    @JsonIgnore
    private List<PlayerMatchStats> bowlers;

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
