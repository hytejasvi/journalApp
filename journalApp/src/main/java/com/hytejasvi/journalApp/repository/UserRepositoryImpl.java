package com.hytejasvi.journalApp.repository;

import com.hytejasvi.journalApp.Entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSentimentAnalysis() {
        Query query = new Query();
/*        query.addCriteria(Criteria.where("email").exists(true));
        query.addCriteria(Criteria.where("email").ne(null).ne("")); //ne--> not equal*/
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$", "i"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        log.info(query.toString());
        List<User> user = mongoTemplate.find(query, User.class);
        log.info(user.toString());
        return user;
    }
}
