package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.command.util.Commands;
import com.courses.management.common.command.util.InputString;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAO;
import com.courses.management.course.CourseStatus;

import java.util.Optional;

public class UpdateCourseStatus implements Command {
    private View view;
    private CourseDAO courseDAO;

    public UpdateCourseStatus(View view, CourseDAO dao) {
        this.view = view;
        this.courseDAO = dao;
    }

    @Override
    public String command() {
        return Commands.UPDATE_COURSE_STATUS;
    }

    @Override
    public void process(InputString input) {
        String title = input.getParameters()[1];
        Course course = courseDAO.get(title);
        if (course == null || course.getTitle() == null) {
            throw new IllegalArgumentException(String.format("Course with title %s not exists", course.getTitle()));
        }
        String status = input.getParameters()[2];
        course.setCourseStatus(getStatus(status));
        courseDAO.update(course);
        view.write("Course status updated.");
    }

    private CourseStatus getStatus(String status) {
        Optional<CourseStatus> courseStatus = CourseStatus.getCourseStatusValue(status);
        return courseStatus.orElseThrow(() ->
                new IllegalArgumentException("Course status is wrong, choose the correct one."));
    }
}
