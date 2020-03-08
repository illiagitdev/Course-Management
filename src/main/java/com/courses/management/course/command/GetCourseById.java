package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAOImpl;

public class GetCourseById implements Command {
    private final View view;
    private DataAccessObject<Course> courseDAO;

    public GetCourseById(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "course_by_id";
    }

    @Override
    public void process() {
        view.write("Enter a course id");
        int id = validateNumber(view.read());
        Course course = courseDAO.get(id);
        view.write("Course\t\tcourse status");
        view.write(String.format("%d\t%s\t%s", course.getId(), course.getTitle(), course.getCourseStatus()));
        view.write(String.format("Course with id = %s retrieved", id));
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
