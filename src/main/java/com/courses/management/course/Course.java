package com.courses.management.course;

import com.courses.management.common.BaseEntity;
import com.courses.management.common.EnumValidator;
import com.courses.management.homework.Homework;
import com.courses.management.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "Course")
public class Course extends BaseEntity implements Serializable {
    private String title;
    private List<User> users;
    private CourseStatus courseStatus;
//    private Calendar calendar;
    private List<Homework> homework;

    public Course() {
    }

    @NotEmpty
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "course")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @EnumValidator(regexp = "NOT_STARTED|IN_PROGRESS")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus status) {
        this.courseStatus = status;
    }

//    public Calendar getCalendar() {
//        return calendar;
//    }

//    public void setCalendar(Calendar calendar) {
//        this.calendar = calendar;
//    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "course")
    public List<Homework> getHomework() {
        return homework;
    }

    public void setHomework(List<Homework> homework) {
        this.homework = homework;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return title.equals(course.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
