package com.devesh.cricket.entity;

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
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PlayerRole role;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
