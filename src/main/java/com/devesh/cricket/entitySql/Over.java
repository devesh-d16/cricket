package com.devesh.cricket.entitySql;

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
    private Long overId;

    private int overNo;

    @Column(columnDefinition = "int default 0")
    private int runs;

    @Column(columnDefinition = "int default 0")
    private int wickets;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inning_id")
    private Inning inning;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bowler_id")
    private PlayerStats bowler;

    @OneToMany(mappedBy = "overs", cascade = CascadeType.ALL)
    private List<Ball> balls;

    public void addRuns(int runs){
        this.runs += runs;
    }

    public void addWicket(){
        this.wickets++;
    }
}

