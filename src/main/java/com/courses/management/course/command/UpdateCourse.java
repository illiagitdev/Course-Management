package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAO;
import com.courses.management.course.CourseDAOImpl;
import com.courses.management.course.CourseStatus;

import java.util.Optional;

public class UpdateCourse implements Command {
    private final View view;
    private CourseDAO courseDAO;

    public UpdateCourse(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "update_course";
    }

    @Override
    public void process() {
        view.write("Enter course ID for update");
        int id = validateNumber(view.read());
        view.write("Enter update to course title");
        String title = validate(view.read());
         view.write("Enter update to course status");
        String value = validate(view.read());
        Course course = new Course();
        course.setId(id);
        course.setTitle(title);
        Optional<CourseStatus> tmp = CourseStatus.getCourseStatusValue(value.toLowerCase());
        CourseStatus status = tmp.isEmpty() ? null : tmp.get();
        course.setCourseStatus(status);
        courseDAO.update(course);
        view.write(String.format("Course updated with title: %s", course.getTitle()));
    }

    private String validate(String value) {
        while (value.trim().isEmpty()){
            value = view.read();
        }
        return value.toUpperCase();
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
