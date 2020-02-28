package com.courses.management.course;

public enum SolutionStatus {
    COMPLETED("COMPLETED"),
    STARTED("STARTED"),
    NEW("NEW");

    private String status;

    public String getStatus() {
        return status;
    }

    SolutionStatus(String status) {
        this.status = status;
    }
}
