package com.devesh.cricket.entitySql;

import com.devesh.cricket.enums.MatchStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "match_details")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    private int overs;

    private String venue;

    private String winningCondition;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime lastUpdate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private MatchStatus matchStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team1_id")
    private TeamStats team1;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team2_id")
    private TeamStats team2;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Inning> innings;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private TeamStats winner;
}
