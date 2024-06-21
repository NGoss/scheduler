package io.foinse.scheduler.util;

import java.time.LocalDateTime;

import io.foinse.scheduler.entities.ConflictInstance;

public class ScheduleUtils {
    public static boolean checkIfConflictInRehearsalTimeRange(ConflictInstance ci, LocalDateTime start, LocalDateTime end) {
        LocalDateTime ciStart = ci.getStart();
        LocalDateTime ciEnd = ci.getEnd();

        return (start.isBefore(ciStart) && end.isAfter(ciStart))
            ||  (start.isBefore(ciEnd) && end.isAfter(ciEnd));
    }
    
}
