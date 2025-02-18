package com.devesh.cricket.model;

import com.devesh.cricket.model.enums.Format;
import com.devesh.cricket.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tournamentId;

    private String tournamentName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String location;
    private int totalMatches;
    private int totalTeams;

    @Enumerated(EnumType.STRING)
    private Format format;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Match> matches;

    @ManyToMany
    @JoinTable(
            name = "tournament_teams",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams;

    @ManyToOne
    @JoinColumn(
            name = "winning_team_id"
    )
    private Team winningTeam;
}

