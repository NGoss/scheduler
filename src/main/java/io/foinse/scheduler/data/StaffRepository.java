package io.foinse.scheduler.data;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import io.foinse.scheduler.entities.Staff;

@Repository
public class StaffRepository {
    private final MongoOperations template;

    public StaffRepository(MongoTemplate mongoTemplate) {
        this.template = mongoTemplate;
    }

    public Staff findById(String id) {
        return this.template.findById(id, Staff.class);
    }

    public Staff create(String name, String type) {
        Staff director = Staff.builder()
                .name(name)
                .type(type)
                .build();
        return this.template.insert(director);
    }
}
