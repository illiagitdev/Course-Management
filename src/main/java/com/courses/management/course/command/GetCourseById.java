package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAO;
import com.courses.management.course.CourseDAOImpl;

public class GetCourseById implements Command {
    private final View view;
    private CourseDAO courseDAO;

    public GetCourseById(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "find_course_id";
    }

    @Override
    public void process() {
        view.write("To find course enter course id");
        int id = validateNumber(view.read());
        Course course = courseDAO.get(id);
        if (course == null){
            view.write(String.format("Course with id=%d not exist!", id));
        }else {
            view.write("Course\t\tcourse status");
            view.write(String.format("%d\t%s\t%s", course.getId(), course.getTitle(), course.getCourseStatus()));
        }
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
