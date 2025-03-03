package com.devesh.cricket.entityMongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "balls")
public class BallMongo {

    @Id
    private Long id;

    private int ballNo;
    private int runs;

    @Field("isWicket")
    private boolean isWicket;

    @Field("over_id")
    private Long overId;

    @Field("batsman_id")
    private Long batsmanId;

    @Field("bowler_id")
    private Long bowlerId;
}
