package com.devesh.cricket.entitySql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class TeamStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamStatId;

    @Column(columnDefinition = "int default 0")
    private int runs;

    @Column(columnDefinition = "int default 0")
    private int wickets;

    @Column(columnDefinition = "int default 0")
    private int overs;

    @Column(columnDefinition = "BIT")
    private boolean isWinner;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private Team team;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id")
    @JsonIgnore
    private Match match;

    @OneToMany(mappedBy = "teamStats", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PlayerStats> players;

    @Transient
    @JsonIgnore
    private List<PlayerStats> bowlers;

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
        for(PlayerStats p : players){
            p.setBallsBowled(0);
            p.setBallsFaced(0);
            p.setRunsConceded(0);
            p.setRunsScored(0);
            p.setWicketsTaken(0);
        }
    }
}
