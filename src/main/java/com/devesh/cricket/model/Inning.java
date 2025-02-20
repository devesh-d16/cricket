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
@Table(name = "inning_details")
public class Inning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inningsId;

    @Column(columnDefinition = "int default 0")
    private int totalRuns = 0;

    @Column(columnDefinition = "int default 0")
    private int totalWickets = 0;

    @Column(columnDefinition = "int default 0")
    private int totalOvers = 0;

    @ManyToOne
    @JoinColumn(name = "match_id")
    @JsonIgnore
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

    // List to store bowlers
    @Transient  // Not stored in the database
    private List<PlayerMatchStats> bowlers;


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
