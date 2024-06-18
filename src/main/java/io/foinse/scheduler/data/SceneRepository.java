package io.foinse.scheduler.data;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import io.foinse.scheduler.entities.Character;
import io.foinse.scheduler.entities.Scene;
import io.foinse.scheduler.entities.Show;

@Repository
public class SceneRepository {
    MongoOperations template;
    public SceneRepository(MongoTemplate mongoTemplate) {
        this.template = mongoTemplate;
    }

    public Scene create(String sceneName) {
        Scene scene = Scene.builder()
                .name(sceneName)
                .build();
        return template.insert(scene);
    }

    public Scene findById(String showId) {
        return template.findById(showId, Scene.class);
    }

    public Scene addCharacter(Scene scene, Character character) {
        scene.getCharacters().add(character);
        return template.save(scene);
    }

    public Scene addCharacters(Scene scene, List<Character> characters) {
        scene.getCharacters().addAll(characters);
        return template.save(scene);
    }

    public Scene removeCharacter(Scene scene, Character character) {
        scene.getCharacters().remove(character);

        return template.save(scene);
    }

    public boolean delete(Scene scene) {
        return template.remove(scene).getDeletedCount() > 0;
    }
}
