package io.foinse.scheduler.data;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import io.foinse.scheduler.entities.Rehearsal;
import io.foinse.scheduler.model.CreateRehearsalRequest;

@Repository
public class RehearsalRepository {
    private final MongoOperations template;
    public RehearsalRepository(MongoTemplate mongoTemplate) {
        this.template = mongoTemplate;
    }

    public Rehearsal create(CreateRehearsalRequest request) {
        Rehearsal rehearsal = Rehearsal.builder()
                .start(request.getStart())
                .end(request.getEnd())
                .type(request.getType())
                .build();
        template.insert(rehearsal);
        return template.findById(rehearsal.getId(), Rehearsal.class);
    }

    public Rehearsal findById(String id) {
        return template.findById(id, Rehearsal.class);
    }

    public Rehearsal save(Rehearsal rehearsal) {
        return template.save(rehearsal);
    }
}
