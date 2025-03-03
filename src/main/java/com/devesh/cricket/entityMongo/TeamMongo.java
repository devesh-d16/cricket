package com.devesh.cricket.entityMongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "teams") // Define the MongoDB collection name
public class TeamMongo {

    @Id
    private Long id;

    @Field("name")
    private String name;

    @Field("player_ids")
    private List<Long> playerIds; // Store only player IDs instead of objects
}
