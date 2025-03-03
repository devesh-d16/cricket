package com.devesh.cricket.repositoryMongo;

import com.devesh.cricket.entityMongo.PlayerStatsMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PlayerStatsMongoRepository extends MongoRepository<PlayerStatsMongo, Long> {

}
