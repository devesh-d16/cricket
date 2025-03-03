package com.devesh.cricket.entityMongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "team_match_stats") // Define the MongoDB collection name
public class TeamStatsMongo {

    @Id
    private Long id;

    @Field("name")
    private String name;

    @Field("runs")
    private int runs = 0;

    @Field("wickets")
    private int wickets = 0;

    @Field("overs")
    private int overs = 0;

    @Field("is_winner")
    private boolean isWinner;

    @Field("team_id")
    private Long teamId; // Store only the team ID instead of the object reference

    @Field("match_id")
    private Long matchId; // Store only the match ID

    @Field("player_ids")
    private List<Long> playerIds; // Store only player IDs instead of objects

    @Field("bowler_ids")
    private List<Long> bowlerIds; // Store only bowler IDs

    public void addRuns(int runs) {
        this.runs += runs;
    }

    public void incrementWickets() {
        this.wickets++;
    }

    public void reset() {
        this.runs = 0;
        this.wickets = 0;
        this.overs = 0;
    }
}
