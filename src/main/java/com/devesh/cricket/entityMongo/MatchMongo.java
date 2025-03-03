package com.devesh.cricket.entityMongo;

import com.devesh.cricket.enums.MatchStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "matches")
public class MatchMongo {

    @Id
    private Long id;

    @Field("match_status")
    private MatchStatus matchStatus;

    @Field("overs")
    private int overs;

    @Field("winning_margin")
    private int winningMargin;

    @Field("winning_condition")
    private String winningCondition;

    @Field("is_completed")
    private boolean isCompleted = false;

    @Field("team1_id")
    private Long team1Id;

    @Field("team2_id")
    private Long team2Id;

    @Field("innings")
    private List<Long> inningIds;

    @Field("winner_id")
    private Long winnerId;
}
