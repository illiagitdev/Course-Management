package com.courses.management.course;

public enum Status {
    OT_STARTED("OT_STARTED"),
    IN_PROGRESS("IN_PROGRESS"),
    FINISHED("FINISHED");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
