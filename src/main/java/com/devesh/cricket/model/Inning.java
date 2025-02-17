package com.devesh.cricket.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Long id;

    private int totalRuns = 0;
    private int totalWickets = 0;
    private int totalOvers = 0;

    // understand
    @ManyToOne
    @JoinColumn(
            name = "match_id"
    )
    private Match match;

    @ManyToOne
    @JoinColumn(
            name = "batting_team_id"
    )
    private Team battingTeam;

    @ManyToOne
    @JoinColumn(
            name = "bowling_team_id"
    )
    private Team bowlingTeam;

    @OneToMany(
            mappedBy = "inning",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Over> overs;


    // methods
    public void addRuns(int runs){
        this.totalRuns += runs;
    }

    public void addWickets(int wickets){
        this.totalWickets += wickets;
    }

    public void addOvers(){
        this.totalOvers++;
    }
}
