package io.foinse.scheduler.service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.foinse.scheduler.data.ActorRepository;
import io.foinse.scheduler.data.CharacterRepository;
import io.foinse.scheduler.entities.Actor;
import io.foinse.scheduler.entities.Character;
import io.foinse.scheduler.entities.ConflictInstance;
import io.foinse.scheduler.model.AddActorRequest;
import io.foinse.scheduler.util.ScheduleUtils;

@Service
public class ActorService {
    private final ActorRepository actorRepository;
    private final CharacterRepository characterRepository;
    public ActorService(ActorRepository actorRepository, CharacterRepository characterRepository) {
        this.actorRepository = actorRepository;
        this.characterRepository = characterRepository;
    }

    public Actor findById(String id) {
        Actor actor = actorRepository.findById(id);

        if (actor == null) {
            throw new ResponseStatusException(NOT_FOUND);
        }

        return actor;
    }

    public Actor create(AddActorRequest request) {
        Actor actor = Actor.builder()
                .name(request.getName())
                .build();
        return actorRepository.create(actor);
    }

    public Actor update(Actor request) {
        return actorRepository.updateActorIfExists(request);
    }

    public boolean delete(String id) {
        Actor actor = actorRepository.findById(id);

        if (actor == null) {
            throw new ResponseStatusException(NOT_FOUND);
        }

        List<Character> characters = characterRepository.findByActorId(id);
        for(Character c : characters) {
            c.setActor(null);
            characterRepository.save(c);
        }

        return actorRepository.delete(actor);
    }

    public List<ConflictInstance> getConflicts(String sid, String actorId, LocalDateTime start, LocalDateTime end) {
        Actor actor = actorRepository.findById(actorId);

        if (actor == null) {
            throw new ResponseStatusException(NOT_FOUND);
        }

        return actor.getConflicts().stream()
                .filter(c -> ScheduleUtils.checkIfConflictInRehearsalTimeRange(c, start, end)).toList();
    }
}
