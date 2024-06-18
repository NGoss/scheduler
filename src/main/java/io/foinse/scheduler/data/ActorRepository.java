package io.foinse.scheduler.data;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import io.foinse.scheduler.entities.Actor;

@Repository
public class ActorRepository {
    private final MongoOperations template;
    public ActorRepository(MongoTemplate mongoTemplate) {
        this.template = mongoTemplate;
    }

    public Actor findById(String id) {
        return template.findById(id, Actor.class);
    }

    public Actor create(Actor actor) {
        template.insert(actor);
        return template.findById(actor.getId(), Actor.class);
    }

    public Actor updateActorIfExists(Actor request) {
        Actor actor = template.findById(request.getId(), Actor.class);
        if (actor != null) {
            actor = request;
            template.save(actor);
        }

        return actor;
    }

    public boolean delete(Actor actor) {
        return template.remove(actor).getDeletedCount() > 0;
    }
}
