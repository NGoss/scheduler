package io.foinse.scheduler.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.foinse.scheduler.data.CharacterRepository;
import io.foinse.scheduler.data.SceneRepository;
import io.foinse.scheduler.data.ShowRepository;
import io.foinse.scheduler.entities.Character;
import io.foinse.scheduler.entities.Scene;
import io.foinse.scheduler.entities.Show;

@Service
public class SceneService {
    private final SceneRepository sceneRepository;
    private final ShowRepository showRepository;
    private final CharacterRepository characterRepository;
    public SceneService(SceneRepository sceneRepository, ShowRepository showRepository,
            CharacterRepository characterRepository) {
        this.sceneRepository = sceneRepository;
        this.showRepository = showRepository;
        this.characterRepository = characterRepository;
    }

    public Scene createSceneForShow(String showId, String sceneName) {
        Show show = showRepository.findById(showId);
        if (show == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Scene createdScene = sceneRepository.create(sceneName);
        if (createdScene != null) {
            show.getScenes().add(createdScene);
            showRepository.save(show);
        }

        return createdScene;
    }

    public Scene addCharacterToScene(String sceneId, String characterId) {
        Scene scene = sceneRepository.findById(sceneId);
        Character character = characterRepository.findById(characterId);

        if (scene == null || character == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return sceneRepository.addCharacter(scene, character);
    }

    public Scene addCharactersToScene(String sceneId, List<String> characterIds) {
        Scene scene = sceneRepository.findById(sceneId);
        List<Character> characters = new ArrayList<>();
        for(String id : characterIds) {
            Character character = characterRepository.findById(id);
            if (character == null ) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            characters.add(character);
        }

        if (scene == null ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return sceneRepository.addCharacters(scene, characters);
    }

    public Scene removeCharacterFromScene(String sceneId, String characterId) {
        Scene scene = sceneRepository.findById(sceneId);
        Character character = characterRepository.findById(characterId);

        if (scene == null || character == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return sceneRepository.removeCharacter(scene, character);
    }

    public boolean deleteScene(String sceneId) {
        Scene scene = sceneRepository.findById(sceneId);

        if (scene == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return sceneRepository.delete(scene);
    }
}
