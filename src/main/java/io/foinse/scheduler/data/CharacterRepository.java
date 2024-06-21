package io.foinse.scheduler.data;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;

import io.foinse.scheduler.entities.Character;

@Repository
public class CharacterRepository {
    private MongoOperations template;
    public CharacterRepository(MongoTemplate mongoTemplate) {
        this.template = mongoTemplate;
    }

    public Character findById(String id) {
        return template.findById(id, Character.class);
    }

    public Character create(String name) {
        Character character = Character.builder()
                .name(name)
                .build();
        return template.insert(character);
    }

    public Character save(Character character) {
        return template.save(character);
    }

    public boolean delete(Character character) {
        DeleteResult result = template.remove(character);

        return result.getDeletedCount() > 0;
    }

    public List<Character> findByActorId(String actorId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("actor").is(actorId));
        return template.find(query, Character.class);
    }
}
