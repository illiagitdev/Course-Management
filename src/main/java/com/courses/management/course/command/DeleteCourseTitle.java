package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.course.CourseDAO;
import com.courses.management.course.CourseDAOImpl;

public class DeleteCourseTitle implements Command {
    private final View view;
    private CourseDAO courseDAO;

    public DeleteCourseTitle(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "delete_course_by_title";
    }

    @Override
    public void process() {
        view.write("Enter a course title");
        String title = validate(view.read());
        courseDAO.delete(title);
        view.write(String.format("Course with title = %s deleted", title));
    }

    private String validate(String value) {
        while (value.trim().isEmpty()){
            value = view.read();
        }
        return value.toUpperCase();
    }
}
