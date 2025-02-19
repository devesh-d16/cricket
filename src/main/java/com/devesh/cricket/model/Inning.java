package com.devesh.cricket.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Inning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inningsId;

    private int totalRuns = 0;
    private int totalWickets = 0;
    private int totalOvers = 0;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "batting_team_id")
    private TeamMatchStats battingTeam;

    @ManyToOne
    @JoinColumn(name = "bowling_team_id")
    private TeamMatchStats bowlingTeam;

    @OneToMany(mappedBy = "inning", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Over> overs;


    public void addRuns(int runs){
        this.totalRuns += runs;
    }

    public void incrementWickets(){
        this.totalWickets++;
    }

    public void addOvers(){
        this.totalOvers++;
    }
}
