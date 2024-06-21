package io.foinse.scheduler.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.foinse.scheduler.data.ActorRepository;
import io.foinse.scheduler.data.RehearsalRepository;
import io.foinse.scheduler.entities.Actor;
import io.foinse.scheduler.entities.Rehearsal;
import io.foinse.scheduler.model.CreateRehearsalRequest;

@Service
public class RehearsalService {
    private RehearsalRepository rehearsalRepository;
    private ActorRepository actorRepository;
    public RehearsalService(RehearsalRepository rehearsalRepository, ActorRepository actorRepository) {
        this.rehearsalRepository = rehearsalRepository;
        this.actorRepository = actorRepository;
    }
    public Rehearsal create(CreateRehearsalRequest request) {
        return rehearsalRepository.create(request);
    }

    public Rehearsal callActorForRehearsal(String rehearsalId, String actorId) {
        Actor actor = actorRepository.findById(actorId);
        Rehearsal rehearsal = rehearsalRepository.findById(rehearsalId);
        if (actor == null || rehearsal == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return rehearsalRepository.save(rehearsal);
    }
}
