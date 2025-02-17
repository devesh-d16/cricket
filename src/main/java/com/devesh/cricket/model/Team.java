package com.devesh.cricket.model;

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
    private Long teamId;

    private String teamName;
    private int totalRuns = 0;
    private int totalWickets = 0;

    @ManyToMany(mappedBy = "teams")
    @JsonIgnore
    private List<Tournament> tournaments;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    private List<Player> players;

    public void addRuns(int runs) {
        this.totalRuns += runs;
    }

    public void incrementWickets() {
        if (this.totalWickets < 10) {
            this.totalWickets++;
        }
    }
}
