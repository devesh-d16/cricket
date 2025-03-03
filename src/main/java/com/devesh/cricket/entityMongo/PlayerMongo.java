package com.devesh.cricket.entityMongo;

import com.devesh.cricket.enums.PlayerRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "players")
public class PlayerMongo {

    @Id
    private Long id;

    @Field("name")
    private String name;

    @Field("role")
    private PlayerRole role;

    @Field("team_id")
    private Long teamId;
}
