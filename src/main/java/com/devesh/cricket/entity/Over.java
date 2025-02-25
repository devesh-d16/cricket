package com.devesh.cricket.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "over_details")
public class Over {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int overNo;

    @Column(columnDefinition = "int default 0")
    private int runs;

    @Column(columnDefinition = "int default 0")
    private int wickets;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "inning_id")
    private Inning inning;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bowler_id")
    private PlayerMatchStats bowler;

    @OneToMany(mappedBy = "over", cascade = CascadeType.ALL)
    private List<Ball> balls;

    public void addRuns(int runs){
        this.runs += runs;
    }

    public void addWicket(){
        this.wickets++;
    }
}

