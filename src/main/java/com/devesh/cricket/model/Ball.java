package com.devesh.cricket.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Ball {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int ballNumber;
    private int overNumber;
    private int runsScored;
    private boolean isWicket;

    // understand
    @ManyToOne
    @JoinColumn(
            name = "over_id"
    )
    private Over over;

    @ManyToOne
    @JoinColumn(
            name = "batsman_id"
    )
    private Player batsman;

    @ManyToOne
    @JoinColumn(
            name = "bowler_id"
    )
    private Player bowler;

}
