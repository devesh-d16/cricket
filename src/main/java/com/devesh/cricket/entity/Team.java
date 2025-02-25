package com.devesh.cricket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Player> players;

}
