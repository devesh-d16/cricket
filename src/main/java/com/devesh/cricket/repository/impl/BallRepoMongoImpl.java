package com.devesh.cricket.repository.impl;

import com.devesh.cricket.entityMongo.BallMongo;
import com.devesh.cricket.entitySql.Ball;
import com.devesh.cricket.repository.BallRepository;
import com.devesh.cricket.repositoryMongo.BallMongoRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public class BallRepoMongoImpl implements BallRepository{

    @Override
    public Ball save(Ball ball) {
        return null;
    }
}
