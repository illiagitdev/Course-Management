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
        return "find_course_title";
    }

    @Override
    public void process() {
        view.write("To find course enter it's title");
        String title = validate(view.read());
        Course course = courseDAO.get(title);
        if (course == null){
            view.write(String.format("Course with title=%s not exist!", title));
        }else {
            view.write("Course\t\tcourse status");
            view.write(String.format("%s\t\t%s", course.getTitle(), course.getCourseStatus()));
        }
    }

    private String validate(String value) {
        while (value.trim().isEmpty()){
            value = view.read();
        }
        return value.toUpperCase();
    }
}
