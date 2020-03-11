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

    }
}
