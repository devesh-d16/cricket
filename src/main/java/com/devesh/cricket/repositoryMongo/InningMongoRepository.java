package com.devesh.cricket.repositoryMongo;

import com.devesh.cricket.entityMongo.InningMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InningMongoRepository extends MongoRepository<InningMongo, Long> {

}
