package com.courses.management.course;

import com.courses.management.common.BaseEntity;

import java.util.List;

public class Homework extends BaseEntity {
    private String title;
    private String path;
    private String text;
    private List<Solutions> solutions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Solutions> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solutions> solutions) {
        this.solutions = solutions;
    }
}
