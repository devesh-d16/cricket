package com.devesh.cricket.repositoryMongo;

import com.devesh.cricket.entityMongo.TeamMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TeamMongoRepository extends MongoRepository<TeamMongo, Long> {

}