package io.foinse.scheduler.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.foinse.scheduler.entities.Rehearsal;
import io.foinse.scheduler.model.CreateRehearsalRequest;
import io.foinse.scheduler.service.RehearsalService;

@RestController
@RequestMapping("/rehearsal")
public class RehearsalController {
    private final RehearsalService rehearsalService;
    public RehearsalController(RehearsalService rehearsalService) {
        this.rehearsalService = rehearsalService;
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public Rehearsal create(@RequestBody CreateRehearsalRequest request) {
        return rehearsalService.create(request);
    }

    @PutMapping("/{rid}/actor/{aid}")
    @CrossOrigin(origins = "http://localhost:5173")
    public Rehearsal callActorForRehearsal(@PathVariable String rid, @PathVariable String aid) {
        return rehearsalService.callActorForRehearsal(rid, aid);
    }
}
