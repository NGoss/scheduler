package io.foinse.scheduler.entities;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document
public class Scene {
    private String id;
    private String name;
    @DocumentReference
    private List<Character> characters;
}
