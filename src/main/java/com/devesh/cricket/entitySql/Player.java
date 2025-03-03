package com.devesh.cricket.entitySql;

import com.devesh.cricket.enums.PlayerRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    @Column(nullable = false, unique = true)
    private String playerName;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PlayerRole playerRole;

}
