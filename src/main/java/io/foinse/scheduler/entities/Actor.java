package io.foinse.scheduler.entities;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class Actor {
    private String id;
    private String name;
    private List<ConflictInstance> conflicts;
}
