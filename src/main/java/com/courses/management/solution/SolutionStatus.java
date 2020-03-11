package com.courses.management.solution;

import java.util.Arrays;
import java.util.Optional;

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

    public static Optional<SolutionStatus> getSolutionStatus(String value) {
        return Arrays.stream(SolutionStatus.values())
                .filter(enumValue -> enumValue.getStatus().equals(value))
                .findAny();
    }
}
