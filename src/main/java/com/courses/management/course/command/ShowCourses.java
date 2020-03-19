package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.command.util.InputString;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAO;
import com.courses.management.course.CourseDAOImpl;
import com.courses.management.course.Courses;

import java.util.List;

public class ShowCourses implements Command {
    private View view;
    private CourseDAO courseDAO;

    public ShowCourses(View view) {
        this.view = view;
        this.courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "show_courses";
    }

    @Override
    public void process(InputString input) {
        input.validateParameters(command());
        List<Course> courses = courseDAO.getAll();
        courses.forEach(course ->  Courses.printCourse(view, course));
    }
}
