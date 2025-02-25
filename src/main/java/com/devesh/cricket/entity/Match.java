package com.devesh.cricket.entity;

import com.devesh.cricket.enums.MatchStatus;
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
    private Long id;

    private MatchStatus matchStatus;
    private int overs;
    private int winningMargin;
    private String winningCondition;

    @Column(columnDefinition = "BIT")
    private boolean isCompleted = false;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "team1_id")
    private TeamMatchStats team1;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "team2_id")
    private TeamMatchStats team2;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Inning> innings;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private TeamMatchStats winner;
}
