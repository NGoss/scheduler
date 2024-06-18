package io.foinse.scheduler.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.foinse.scheduler.data.ActorRepository;
import io.foinse.scheduler.data.CharacterRepository;
import io.foinse.scheduler.entities.Actor;
import io.foinse.scheduler.entities.Character;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final ActorRepository actorRepository;

    public CharacterService(CharacterRepository characterRepository, ActorRepository actorRepository) {
        this.characterRepository = characterRepository;
        this.actorRepository = actorRepository;
    }
    public Character castActorAsCharacter(String characterId, String actorId) {
        Character character = characterRepository.findById(characterId);
        Actor actor = actorRepository.findById(actorId);

        if (character == null || actor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        character.setActor(actor);
        return characterRepository.save(character);
    }

    public Character uncastCharacter(String characterId) {
        Character character = characterRepository.findById(characterId);

        if (character == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        character.setActor(null);
        return characterRepository.save(character);
    }

    public boolean delete(String sid, String characterId) {
        Character character = characterRepository.findById(characterId);

        if (character == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return characterRepository.delete(character);
    }
}
