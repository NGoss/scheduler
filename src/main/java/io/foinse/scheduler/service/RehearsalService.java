package io.foinse.scheduler.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.foinse.scheduler.data.ActorRepository;
import io.foinse.scheduler.data.RehearsalRepository;
import io.foinse.scheduler.data.SceneRepository;
import io.foinse.scheduler.data.ShowRepository;
import io.foinse.scheduler.entities.Actor;
import io.foinse.scheduler.entities.Rehearsal;
import io.foinse.scheduler.entities.Scene;
import io.foinse.scheduler.entities.Show;
import io.foinse.scheduler.model.CreateRehearsalRequest;

@Service
public class RehearsalService {
    private RehearsalRepository rehearsalRepository;
    private ActorRepository actorRepository;
    private ShowRepository showRepository;
    private SceneRepository sceneRepository;

    public RehearsalService(RehearsalRepository rehearsalRepository, ActorRepository actorRepository,
            ShowRepository showRepository, SceneRepository sceneRepository) {
        this.rehearsalRepository = rehearsalRepository;
        this.actorRepository = actorRepository;
        this.showRepository = showRepository;
        this.sceneRepository = sceneRepository;
    }
    public Rehearsal create(CreateRehearsalRequest request) {
        Show s = showRepository.findById(request.getShowId());
        List<Scene> scenesList = new ArrayList<>();
        for (String sceneId : request.getSceneIds()) {
            Scene scene = sceneRepository.findById(sceneId);
            if (scene != null) {
                scenesList.add(scene);
            }
        }

        if (s == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Rehearsal r = rehearsalRepository.create(request, scenesList);
        if (r != null) {
            s.getRehearsals().add(r);
            showRepository.save(s);
        }

        return r;
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
