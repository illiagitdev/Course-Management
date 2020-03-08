package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAO;
import com.courses.management.course.CourseDAOImpl;

public class GetCourseByTitle implements Command {
    private final View view;
    private CourseDAO courseDAO;

    public GetCourseByTitle(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "course_by_title";
    }

    @Override
    public void process() {
        view.write("Enter course title");
        String title = validate(view.read());
        Course course = courseDAO.get(title);
        view.write("Course\t\tcourse status");
        view.write(String.format("%s\t\t%s", course.getTitle(), course.getCourseStatus()));
        view.write(String.format("Course retrieved by title: %s", title));
    }

    private String validate(String value) {
        while (value.trim().isEmpty()){
            value = view.read();
        }
        return value.toUpperCase();
    }
}
