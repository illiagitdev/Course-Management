package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.command.util.InputString;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAO;
import com.courses.management.course.CourseDAOImpl;
import com.courses.management.course.CourseStatus;

public class CreateCourse implements Command {
    private final View view;
    private CourseDAO courseDAO;

    public CreateCourse(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "create_course|title";
    }

    @Override
    public void process(InputString input) {
        Course course = mapCourse(input);
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

    private Course mapCourse(InputString input) {
        String[] parameters = input.getParameters();
        String title = parameters[1];
        Course course = new Course();
        course.setTitle(title);
        course.setCourseStatus(CourseStatus.NOT_STARTED);
        return course;
    }
}
