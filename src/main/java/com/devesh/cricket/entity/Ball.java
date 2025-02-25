package com.devesh.cricket.entity;

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
    private Long id;

    private int ballNo;
    private int runs;

    @Column(columnDefinition = "BIT")
    private boolean isWicket;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "over_id")
    private Over over;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "batsman_id")
    private PlayerMatchStats batsman;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY                                                                                                                                            )
    @JoinColumn(name = "bowler_id")
    private PlayerMatchStats bowler;
}
