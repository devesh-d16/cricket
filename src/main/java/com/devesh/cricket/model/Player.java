package com.devesh.cricket.model;

import com.devesh.cricket.enums.PlayerRole;
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
    @Enumerated(EnumType.STRING)
    private PlayerRole playerRole;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
