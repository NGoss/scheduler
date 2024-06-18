package io.foinse.scheduler.model;

import lombok.Data;

@Data
public class AssignCharacterRequest {
    private String actorId;
    private String character;
}
