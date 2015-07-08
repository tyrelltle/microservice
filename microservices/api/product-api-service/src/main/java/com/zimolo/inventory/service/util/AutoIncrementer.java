package com.zimolo.inventory.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component

public class AutoIncrementer {

    @Autowired
    private MongoTemplate mongoTemplate;
    private final Logger log = LoggerFactory.getLogger(AutoIncrementer.class);
    public static final int INITIAL=100000;
    public static final int INCR_STEP=1;

    private int a;
    @PostConstruct
    public void initialize_if_need(){
        long count=mongoTemplate.count(new Query(),Counter.class);
        log.debug("AutoIncrementer --> count="+count);

        if(count>0)
            return;

        Counter c = new Counter();
        c.setId("Product");
        c.setSeq(INITIAL);
        mongoTemplate.save(c);
        log.debug("AutoIncrementer --> new Counter entry for Product");

    }
    public int getNextSequence(String collectionName) {
        initialize_if_need();
        Counter counter = mongoTemplate.findAndModify(
            query(where("_id").is(collectionName)),
            new Update().inc("seq", INCR_STEP),
            options().returnNew(true),
             Counter.class);

        return counter.getSeq();
     }

    @Document(collection = "counters")
    public static class Counter {
        @Id
        private String id;
        private int seq;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }
    }

}
