package com.devesh.cricket.entityMongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "overs")
public class OverMongo {

    @Id
    private Long id;

    @Field("over_no")
    private int overNo;

    @Field("runs")
    private int runs = 0;

    @Field("wickets")
    private int wickets = 0;

    @Field("inning_id")
    private Long inningId;

    @Field("bowler_id")
    private Long bowlerId;

    @Field("ball_ids")
    private List<Long> ballIds;

    public void addRuns(int runs) {
        this.runs += runs;
    }

    public void addWicket() {
        this.wickets++;
    }
}
