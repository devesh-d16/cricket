package com.devesh.cricket.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Over {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int overNumber;
    private int runsScored = 0;
    private int wicketsInTheOver = 0;

    // understand
    @ManyToOne
    @JoinColumn(
            name = "inning_id"
    )
    private Inning inning;

    @ManyToOne
    @JoinColumn(
            name = "bowler_id"
    )
    private Player bowler;

    @OneToMany(
            mappedBy = "over",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Ball> balls;

    // methods
    public void addRuns(int runs){
        this.runsScored += runs;
    }

    public void addWicket(){
        this.wicketsInTheOver++;
    }
}
