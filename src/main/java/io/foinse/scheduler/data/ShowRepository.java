package io.foinse.scheduler.data;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import io.foinse.scheduler.entities.Show;
import io.foinse.scheduler.model.CreateShowRequest;

@Repository
public class ShowRepository {
    private final MongoOperations template;
    public ShowRepository(MongoTemplate mongoTemplate) {
        this.template = mongoTemplate;
    }

    public Show create(CreateShowRequest request) {

        Show show = Show.builder()
                .name(request.getName())
                .build();

        template.insert(show);

        return template.findById(show.getId(), Show.class);
    }

    public List<Show> findAll() {
        return template.findAll(Show.class);
    }
    public Show findById(String id) {
        return template.findById(id, Show.class);
    }

    public Show save(Show show) {
        return template.save(show);
    }
}
