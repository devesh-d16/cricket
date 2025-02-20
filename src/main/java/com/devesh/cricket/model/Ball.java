package com.devesh.cricket.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "ball_details")
public class Ball {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ballId;

    private int ballNumber;
    private int runsScored;

    @Column(columnDefinition = "BIT")
    private boolean isWicket;

    @ManyToOne
    @JoinColumn(name = "over_id")
    private Over over;

    @ManyToOne
    @JoinColumn(name = "batsman_stats_id")
    private PlayerMatchStats batsman;

    @ManyToOne
    @JoinColumn(name = "bowler_stats_id")
    private PlayerMatchStats bowler;
}
