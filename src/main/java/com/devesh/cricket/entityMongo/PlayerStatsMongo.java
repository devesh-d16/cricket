package com.devesh.cricket.entityMongo;

import com.devesh.cricket.enums.PlayerRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "players_match_stats")
public class PlayerStatsMongo {

    @Id
    private Long id;

    @Field("name")
    private String name;

    @Field("runsScored")
    private int runsScored = 0;

    @Field("ballsFaced")
    private int ballsFaced;

    @Field("wicketsTaken")
    private int wicketsTaken = 0;

    @Field("ballsBowled")
    private int ballsBowled = 0;

    @Field("runsConceded")
    private int runsConceded = 0;

    @Field("role")
    private PlayerRole playerRole;

    @Field("player_id")
    private Long playerId;

    @Field("team_id")
    private Long teamId;

    public void addRuns(int runs) {
        this.runsScored += runs;
    }

    public void incrementBallFaced(){
        this.ballsFaced++;
    }

    public void incrementWicketTaken() {
        this.wicketsTaken++;
    }

    public void incrementBallsBowled() {
        this.ballsBowled++;
    }

    public void addRunsConceded(int run) {
        this.runsConceded += run;
    }
}
