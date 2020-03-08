package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAOImpl;
import com.courses.management.course.CourseStatus;

public class CreateCourse implements Command {
    private final View view;
    private DataAccessObject<Course> courseDAO;

    public CreateCourse(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "create_course";
    }

    @Override
    public void process() {
            view.write("Enter a course title");
            String title = validate(view.read());
            Course course = new Course();
            course.setTitle(title);
            course.setCourseStatus(CourseStatus.NOT_STARTED);
            courseDAO.create(course);
            view.write(String.format("Course created with title: %s", course.getTitle()));
    }

    private String validate(String value) {
        while (value.trim().isEmpty()){
            view.write("Please enter the correct title!");
            value = view.read();
        }
        return value;
    }
}
