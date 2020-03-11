package com.courses.management.course;

import java.util.Arrays;
import java.util.Optional;

public enum CourseStatus {
    NOT_STARTED("NOT_STARTED"),
    IN_PROGRESS("IN_PROGRESS"),
    FINISHED("FINISHED");

    private String status;

    CourseStatus(String status) {
        this.status = status;
    }

    public String getCourseStatus() {
        return status;
    }

    public static Optional<CourseStatus> getCourseStatusValue(String value) {
        return Arrays.stream(CourseStatus.values())
                .filter(enumValue -> enumValue.getCourseStatus().equals(value))
                .findAny();
    }
}
