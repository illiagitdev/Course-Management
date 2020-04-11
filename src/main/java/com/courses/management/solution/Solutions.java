package com.courses.management.solution;

import com.courses.management.common.BaseEntity;
import com.courses.management.homework.Homework;
import com.courses.management.user.User;

import javax.persistence.*;

@Entity
@Table(name = "solution")
public class Solutions extends BaseEntity {
    private String text;
    private SolutionStatus status;
    private int mark;
    private Homework homework;
    private User user;

    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public SolutionStatus getStatus() {
        return status;
    }

    public void setStatus(SolutionStatus status) {
        this.status = status;
    }

    @Column(name = "mark")
    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @ManyToOne
    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
