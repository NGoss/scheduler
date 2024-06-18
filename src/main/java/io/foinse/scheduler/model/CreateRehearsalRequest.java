package io.foinse.scheduler.model;

import java.time.LocalDateTime;
import java.util.List;

import io.foinse.scheduler.entities.RehearsalType;
import lombok.Data;

@Data
public class CreateRehearsalRequest {
    private RehearsalType type;
    private LocalDateTime start;
    private LocalDateTime end;
    private String location;
    private List<String> sceneIds;
}
