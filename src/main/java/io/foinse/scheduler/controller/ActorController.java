package io.foinse.scheduler.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
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

import io.foinse.scheduler.entities.Actor;
import io.foinse.scheduler.entities.ConflictInstance;
import io.foinse.scheduler.entities.Show;
import io.foinse.scheduler.model.AddActorRequest;
import io.foinse.scheduler.service.ActorService;
import io.foinse.scheduler.service.ShowService;

@Controller
@RequestMapping("/show/{sid}/actor")
public class ActorController {

    private final ShowService showService;
    private final ActorService actorService;

    public ActorController(ShowService showService, ActorService actorService) {
        this.showService = showService;
        this.actorService = actorService;
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public Show addActorToShow(@PathVariable String sid, @RequestBody AddActorRequest request) {
        Actor actor = actorService.create(request);
        return showService.addActorToShow(sid, actor.getId());
    }

    @DeleteMapping("/{actorId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public boolean deleteActor(@PathVariable String sid, @PathVariable String actorId) {
        return actorService.delete(actorId);
    }

    @PutMapping("/{actorId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public Actor updateActor(@PathVariable String sid, @PathVariable String actorId, @RequestBody
            Actor request) {
        return actorService.update(request);
    }

    @GetMapping("/{actorId}/conflicts")
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
