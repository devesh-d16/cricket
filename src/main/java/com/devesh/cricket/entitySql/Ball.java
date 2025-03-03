package com.devesh.cricket.entitySql;

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
    private int runs;

    @Column(columnDefinition = "BIT")
    private boolean isWicket;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "over_id")
    private Over overs;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "batsman_id")
    private PlayerStats batsman;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bowler_id")
    private PlayerStats bowler;
}
