package com.devesh.cricket.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "over_id")
    private Over over;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "batsman_stats_id")
    private PlayerMatchStats batsman;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bowler_stats_id")
    private PlayerMatchStats bowler;
}
