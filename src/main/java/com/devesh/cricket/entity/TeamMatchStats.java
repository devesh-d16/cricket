package com.devesh.cricket.entity;

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
    private Long id;

    private String name;

    @Column(columnDefinition = "int default 0")
    private int runs;

    @Column(columnDefinition = "int default 0")
    private int wickets;

    @Column(columnDefinition = "int default 0")
    private int overs;

    @Column(columnDefinition = "BIT")
    private boolean isWinner;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private Team team;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    @JsonIgnore
    private Match match;

    @OneToMany(mappedBy = "teamMatchStats", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PlayerMatchStats> players;

    @Transient
    @JsonIgnore
    private List<PlayerMatchStats> bowlers;

    public void addRuns(int runs) {
        this.runs += runs;
    }

    public void incrementWickets() {
        this.wickets++;
    }

    public void reset(){
        this.runs = 0;
        this.wickets = 0;
        this.overs = 0;
        for(PlayerMatchStats p : players){
            p.setBallsBowled(0);
            p.setBallsFaced(0);
            p.setRunsConceded(0);
            p.setRunsScored(0);
            p.setWicketsTaken(0);
        }
    }
}
