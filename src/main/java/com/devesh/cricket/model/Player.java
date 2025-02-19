package com.devesh.cricket.model;

import com.devesh.cricket.model.enums.PlayerRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    private String playerName;

    private int runsScored = 0;
    private int ballsFaced = 0;
    private int wicketsTaken = 0;
    private int ballsBowled = 0;
    private int runsConceded = 0;

    @Enumerated(EnumType.STRING)
    private PlayerRole playerRole;

    // understand
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    // methods
    public void addRuns(int runs){
        this.runsScored += runs;
    }

    public void addBallFaced(int balls){
        this.ballsFaced += balls;
    }

    public void incrementBallFaced(){
        this.ballsFaced++;
    }

}
