package com.courses.management.course;

import com.courses.management.common.BaseEntity;

public class Solutions extends BaseEntity {
    private String text;
    private SolutionStatus status;
    private int mark;
    private Homework homework;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SolutionStatus getStatus() {
        return status;
    }

    public void setStatus(SolutionStatus status) {
        this.status = status;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }
}
