package com.devesh.cricket.repositoryMongo;

import com.devesh.cricket.entityMongo.TeamStatsMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamStatsMongoRepository extends MongoRepository<TeamStatsMongo, Long> {
}
