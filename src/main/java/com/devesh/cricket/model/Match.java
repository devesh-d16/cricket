package com.devesh.cricket.model;

import com.devesh.cricket.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    private Status matchStatus;
    private int overs;
    private boolean isCompleted = false;
    private int winningMargin;
    private String winningCondition;


    // understand
    @ManyToOne
    @JoinColumn(
            name = "tournament_id"
    )
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(
            name = "team1_id"
    )
    private Team team1;

    @ManyToOne
    @JoinColumn(
            name = "team2_id"
    )
    private Team team2;

    @OneToMany(
            mappedBy = "match",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Inning> innings;

    @ManyToOne
    @JoinColumn(
            name = "winning_team_id"
    )
    private Team winningTeam;

}
