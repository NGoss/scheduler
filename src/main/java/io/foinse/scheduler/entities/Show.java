package io.foinse.scheduler.entities;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.Builder;
import lombok.Data;

@Document
@Builder
@Data
public class Show {
    private String id;
    private String name;
    @DocumentReference
    private List<Scene> scenes;
    @DocumentReference
    private List<Actor> actors;
    @DocumentReference
    private List<Rehearsal> rehearsals;
    @DocumentReference
    private List<Staff> staff;
    @DocumentReference
    private List<Character> characters;
}
