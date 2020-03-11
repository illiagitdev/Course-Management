package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAO;
import com.courses.management.course.CourseDAOImpl;

import java.util.List;

public class GetAllCourses implements Command {
    private final View view;
    private CourseDAO courseDAO;

    public GetAllCourses(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "find_all_courses";
    }

    @Override
    public void process() {
        List<Course> courses = courseDAO.getAll();
        view.write("Course\t\tcourse status");
        for (Course c: courses) {
            view.write(String.format("%s\t\t%s", c.getTitle(), c.getCourseStatus()));
        }
    }
}
