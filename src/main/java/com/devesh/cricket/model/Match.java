package com.devesh.cricket.model;

import com.devesh.cricket.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "match_details")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    private Status matchStatus;
    private int overs;
    private int winningMargin;
    private String winningCondition;

    @Column(columnDefinition = "BIT")
    private boolean isCompleted = false;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team1_id")
    private TeamMatchStats team1;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team2_id")
    private TeamMatchStats team2;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Inning> innings;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "winning_team_id")
    private TeamMatchStats winningTeam;
}
