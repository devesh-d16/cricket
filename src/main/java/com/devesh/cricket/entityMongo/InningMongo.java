package com.devesh.cricket.entityMongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "innings")
public class InningMongo {

    @Id
    private Long id;

    @Field("runs")
    private int runs = 0;

    @Field("wickets")
    private int wickets = 0;

    @Field("overs")
    private int overs = 0;

    @Field("match_id")
    private Long matchId;

    @Field("batting_team_id")
    private Long battingTeamId;

    @Field("bowling_team_id")
    private Long bowlingTeamId;

    @Field("all_overs")
    private List<Long> overIds;

    public void addRuns(int runs) {
        this.runs += runs;
    }

    public void incrementWickets() {
        this.wickets++;
    }

    public void addOvers() {
        this.overs++;
    }
}
