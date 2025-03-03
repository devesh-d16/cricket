package com.devesh.cricket.repositoryMongo;

import com.devesh.cricket.enums.MatchStatus;
import com.devesh.cricket.entityMongo.MatchMongo;
import com.devesh.cricket.entityMongo.TeamMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchMongoRepository extends MongoRepository<MatchMongo, Long> {

}
