package io.foinse.scheduler.entities;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class Staff {
    private String id;
    private String name;
    private String type;
    private List<LocalDate> conflicts;
}
