package io.foinse.scheduler.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.foinse.scheduler.data.StaffRepository;
import io.foinse.scheduler.entities.Staff;

@Service
public class StaffService {
    private final StaffRepository staffRepository;
    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public Staff find(String id) {
        Staff staff = staffRepository.findById(id);

        if (staff == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return staff;
    }

    public Staff create(String name, String type) {
        return staffRepository.create(name, type);
    }
}
