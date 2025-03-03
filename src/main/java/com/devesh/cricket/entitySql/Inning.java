package com.devesh.cricket.entitySql;


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
    private Long inningId;

    @Column(columnDefinition = "int default 0")
    private int runs;

    @Column(columnDefinition = "int default 0")
    private int wickets;

    @Column(columnDefinition = "int default 0")
    private int overs;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id")
    @JsonIgnore
    private Match match;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "batting_team_id")
    private TeamStats battingTeam;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bowling_team_id")
    private TeamStats bowlingTeam;

    @OneToMany(mappedBy = "inning", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Over> overList;

    public void addRuns(int runs){
        this.runs += runs;
    }

    public void incrementWickets(){
        this.wickets++;
    }

    public void addOvers(){
        this.overs++;
    }
}
