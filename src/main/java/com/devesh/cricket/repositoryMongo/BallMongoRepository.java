package com.devesh.cricket.repositoryMongo;

import com.devesh.cricket.entityMongo.BallMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BallMongoRepository extends MongoRepository<BallMongo, Long> {
}
