package io.foinse.scheduler.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class Rehearsal {
    private String id;
    private LocalDateTime start;
    private LocalDateTime end;
    private RehearsalType type;
    @DocumentReference
    private List<Actor> actors;
    private String location;
}
