package io.foinse.scheduler.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.foinse.scheduler.data.ActorRepository;
import io.foinse.scheduler.data.CharacterRepository;
import io.foinse.scheduler.data.StaffRepository;
import io.foinse.scheduler.data.SceneRepository;
import io.foinse.scheduler.data.ShowRepository;
import io.foinse.scheduler.entities.Actor;
import io.foinse.scheduler.entities.Character;
import io.foinse.scheduler.entities.Show;
import io.foinse.scheduler.entities.Staff;
import io.foinse.scheduler.model.CreateShowRequest;

@Service
public class ShowService {
    private final ShowRepository showRepository;
    private final StaffRepository staffRepository;
    private final SceneRepository sceneRepository;
    private final ActorRepository actorRepository;
    private final CharacterRepository characterRepository;

    public ShowService(ShowRepository showRepository, StaffRepository staffRepository,
            SceneRepository sceneRepository, ActorRepository actorRepository,
            CharacterRepository characterRepository) {
        this.showRepository = showRepository;
        this.staffRepository = staffRepository;
        this.sceneRepository = sceneRepository;
        this.actorRepository = actorRepository;
        this.characterRepository = characterRepository;
    }

    public List<Show> findAll() {
        return showRepository.findAll();
    }

    public Show findById(String id) {
        return showRepository.findById(id);
    }

    public Show create(CreateShowRequest request) {
        return showRepository.create(request);
    }

    public Show addStaffToShow(String showId, String staffName, String staffType) {
        Show show = showRepository.findById(showId);
        Staff staff = staffRepository.create(staffName, staffType);

        if (show == null || staff == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        show.getStaff().add(staff);
        return showRepository.save(show);
    }

    public Show addActorToShow(String showid, String actorId) {
        Show show = showRepository.findById(showid);
        Actor actor = actorRepository.findById(actorId);

        if (show == null || actor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        show.getActors().add(actor);
        return showRepository.save(show);
    }

    public Character addCharacterToShow(String showId, String name) {
        Character character = characterRepository.create(name);
        Show show = showRepository.findById(showId);

        if (show == null || character == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        show.getCharacters().add(character);
        showRepository.save(show);
        return character;
    }
}
