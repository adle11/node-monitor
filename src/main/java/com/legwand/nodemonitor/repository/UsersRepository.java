package com.legwand.nodemonitor.repository;

import com.legwand.nodemonitor.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class UsersRepository {

    @Autowired
    private MongoOperations mongoOperations;

    public User getUser(String username) {
      return mongoOperations.findOne(query(where("username").is(username)), User.class);
    }

    public boolean userExists(String username) {
        return mongoOperations.exists(query(where("username").is(username)), User.class);
    }


}
