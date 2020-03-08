package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAOImpl;

public class DeleteCourse implements Command {
    private final View view;
    private DataAccessObject<Course> courseDAO;

    public DeleteCourse(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "delete_course";
    }

    @Override
    public void process() {
        view.write("Enter a course id");
        int id = validateNumber(view.read());
        courseDAO.delete(id);
        view.write(String.format("Course with id = %s deleted", id));
    }

    private int validateNumber(String value) {
        int res = 0;
        try {
            res = Integer.parseInt(value);
        } catch (RuntimeException e) {
            view.write("Input not a number!");
        }
        return res;
    }
}
