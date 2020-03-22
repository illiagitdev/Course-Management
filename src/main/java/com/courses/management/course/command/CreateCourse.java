package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.command.util.Commands;
import com.courses.management.common.command.util.InputString;
import com.courses.management.course.*;

public class CreateCourse implements Command {
    private final View view;
    private CourseDAO courseDAO;

    public CreateCourse(View view, CourseDAO dao) {
        this.view = view;
        this.courseDAO = dao;
    }

    @Override
    public String command() {
        return Commands.CREATE_COURSE;
    }

    @Override
    public void process(InputString input) {
        Course course = Courses.mapCourse(input);
        validateTitle(course.getTitle());
        courseDAO.create(course);
        view.write(String.format("Course created with title: %s", course.getTitle()));
    }

    private void validateTitle(String title) {
        Course course = courseDAO.get(title);
        if (course != null) {
            throw new IllegalArgumentException(String.format("Course with title %s exists", title));
        }
    }
}
