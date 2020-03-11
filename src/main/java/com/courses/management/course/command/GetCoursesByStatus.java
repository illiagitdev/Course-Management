package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAO;
import com.courses.management.course.CourseDAOImpl;
import com.courses.management.course.CourseStatus;

import java.util.List;
import java.util.Optional;

public class GetCoursesByStatus implements Command {
    private final View view;
    private CourseDAO courseDAO;

    public GetCoursesByStatus(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "find_courses_by_status";
    }

    @Override
    public void process() {
        view.write("Enter course status");
        String value = validate(view.read());
        Optional<CourseStatus> tmp = CourseStatus.getCourseStatusValue(value.toUpperCase());
        CourseStatus status = tmp.isEmpty() ? null : tmp.get();
        if (status != null) {
            List<Course> courses = courseDAO.getAll(status.getCourseStatus());
            view.write("Course\t\tcourse status");
            for (Course c: courses) {
                view.write(String.format("%s\t\t%s", c.getTitle(), c.getCourseStatus()));
            }
        }else {
            view.write("No such status exist!");
        }
    }

    private String validate(String value) {
        while (value.trim().isEmpty()){
            value = view.read();
        }
        return value;
    }
}
