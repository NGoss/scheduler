package io.foinse.scheduler.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.foinse.scheduler.entities.Actor;
import io.foinse.scheduler.entities.Character;
import io.foinse.scheduler.entities.ConflictInstance;
import io.foinse.scheduler.entities.Scene;
import io.foinse.scheduler.entities.Show;
import io.foinse.scheduler.model.AddActorRequest;
import io.foinse.scheduler.model.AddCharacterRequest;
import io.foinse.scheduler.model.AddCharacterToSceneRequest;
import io.foinse.scheduler.model.AddStaffRequest;
import io.foinse.scheduler.model.CreateSceneRequest;
import io.foinse.scheduler.model.CreateShowRequest;
import io.foinse.scheduler.service.ActorService;
import io.foinse.scheduler.service.CharacterService;
import io.foinse.scheduler.service.SceneService;
import io.foinse.scheduler.service.ShowService;

@RestController
@RequestMapping("/show")
public class ShowController {

    private final ShowService showService;
    private final SceneService sceneService;
    private final ActorService actorService;

    private final CharacterService characterService;

    public ShowController(ShowService showService, SceneService sceneService, CharacterService characterService, ActorService actorService) {
        this.showService = showService;
        this.sceneService = sceneService;
        this.characterService = characterService;
        this.actorService = actorService;
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public List<Show> findAll() {
        return showService.findAll();
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:5173")
    public Show find(@PathVariable String id) {
        return showService.findById(id);
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public Show create(@RequestBody CreateShowRequest request) {
        return showService.create(request);
    }

    @PutMapping("/{sid}/staff")
    @CrossOrigin(origins = "http://localhost:5173")
    public Show addStaffToShow(@PathVariable String sid, @RequestBody AddStaffRequest request) {
        return showService.addStaffToShow(sid, request.getName(), request.getType());
    }

    @PostMapping("/{sid}/scene")
    @CrossOrigin(origins = "http://localhost:5173")
    public Scene createSceneForShow(@RequestBody CreateSceneRequest request, @PathVariable String sid) {
        return sceneService.createSceneForShow(sid, request.getName());
    }

    @DeleteMapping("/{sid}/scene/{sceneId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public boolean deleteSceneFromShow(@PathVariable String sid, @PathVariable String sceneId) {
        return sceneService.deleteScene(sceneId);
    }

    @PutMapping("/{sid}/scene/{sceneId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public Scene addCharacterToScene(@RequestBody AddCharacterToSceneRequest request,
            @PathVariable String sid, @PathVariable String sceneId) {
        return sceneService.addCharacterToScene(sceneId, request.getCharacterId());
    }

    @PostMapping("/{sid}/character")
    @CrossOrigin(origins = "http://localhost:5173")
    public Character addCharacterToShow(@PathVariable String sid, @RequestBody AddCharacterRequest request) {
        return showService.addCharacterToShow(sid, request.getName());
    }

    @PutMapping("/{sid}/scene/{sceneId}/character/{characterId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public Scene addCharacterToScene(@PathVariable String sid, @PathVariable String sceneId,
            @PathVariable String characterId) {
        return sceneService.addCharacterToScene(sceneId, characterId);
    }

    @PutMapping("/{sid}/scene/{sceneId}/characters")
    @CrossOrigin(origins = "http://localhost:5173")
    public Scene addCharacterToScene(@PathVariable String sid, @PathVariable String sceneId,
            @RequestBody List<String> characterIds) {
        return sceneService.addCharactersToScene(sceneId, characterIds);
    }

    @DeleteMapping("/{sid}/scene/{sceneId}/character/{characterId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public Scene removeCharacterFromScene(@PathVariable String sid, @PathVariable String sceneId,
            @PathVariable String characterId) {
        return sceneService.removeCharacterFromScene(sceneId, characterId);
    }

    @PutMapping("/{sid}/character/{characterId}/actor/{actorId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public Character castActorAsCharacter(@PathVariable String sid,
            @PathVariable String characterId, @PathVariable String actorId) {
        return characterService.castActorAsCharacter(characterId, actorId);
    }

    @DeleteMapping("/{sid}/character/{characterId}/actor")
    @CrossOrigin(origins = "http://localhost:5173")
    public Character uncastCharacter(@PathVariable String sid,
            @PathVariable String characterId) {
        return characterService.uncastCharacter(characterId);
    }

    @DeleteMapping("/{sid}/character/{characterId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public boolean deleteCharacter(@PathVariable String sid, @PathVariable String characterId) {
        return characterService.delete(sid, characterId);
    }

    @PostMapping("/{sid}/actor")
    @CrossOrigin(origins = "http://localhost:5173")
    public Show addActorToShow(@PathVariable String sid, @RequestBody AddActorRequest request) {
        Actor actor = actorService.create(request);
        return showService.addActorToShow(sid, actor.getId());
    }

    @DeleteMapping("/{sid}/actor/{actorId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public boolean deleteActor(@PathVariable String sid, @PathVariable String actorId) {
        return actorService.delete(actorId);
    }

    @PutMapping("/{sid}/actor/{actorId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public Actor updateActor(@PathVariable String sid, @PathVariable String actorId, @RequestBody
            Actor request) {
        return actorService.update(request);
    }

    @GetMapping("/{sid}/actor/{actorId}/conflicts")
    @CrossOrigin(origins = "http://localhost:5173")
    public List<ConflictInstance> getActorConflicts(@PathVariable String sid, @PathVariable String actorId,
    @RequestParam(value="start", required=false) String start, @RequestParam(value="end", required=false) String end) {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startDt = null;
        LocalDateTime endDt = null;

        if (StringUtils.hasText(start)) {
            startDt = dateTimeFormat.parse(start, LocalDateTime::from);
            endDt = dateTimeFormat.parse(end, LocalDateTime::from);
        }

        return actorService.getConflicts(sid, actorId, startDt, endDt);
    }
}
