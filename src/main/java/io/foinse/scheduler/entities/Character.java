package io.foinse.scheduler.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class Character {
    private String id;
    private String name;
    @DocumentReference
    private Actor actor;
}
