package io.foinse.scheduler.entities;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import lombok.Data;

@Data
public class ConflictInstance {
    private String id = new ObjectId().toString();
    private LocalDateTime start;
    private LocalDateTime end;
}
