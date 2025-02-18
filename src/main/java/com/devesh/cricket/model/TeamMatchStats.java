package com.devesh.cricket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
//@Entity
public class TeamMatchStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalRuns = 0;
    private int totalWickets = 0;
    private int totalOvers = 0;
    private boolean isWinner;

    @ManyToOne
    @JoinColumn(
            name = "team_id"
    )
    private Team team;

    @ManyToOne
    @JoinColumn(
            name = "match_id"
    )
    private Match match;


    @OneToMany(mappedBy = "teamMatchStats")
    @JsonIgnore
    private List<PlayerMatchStats> players;

    public void addRuns(int runs) {
        this.totalRuns += runs;
    }

    public void addWicket() {
        this.totalWickets++;
    }
}
