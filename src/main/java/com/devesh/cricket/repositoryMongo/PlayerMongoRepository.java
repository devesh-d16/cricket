package com.devesh.cricket.repositoryMongo;

import com.devesh.cricket.entityMongo.PlayerMongo;
import com.devesh.cricket.enums.PlayerRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerMongoRepository extends MongoRepository<PlayerMongo, Long> {


}
