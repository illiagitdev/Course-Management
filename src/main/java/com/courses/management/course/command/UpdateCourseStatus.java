package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAO;
import com.courses.management.course.CourseDAOImpl;
import com.courses.management.course.CourseStatus;

import java.util.Optional;

public class UpdateCourseStatus implements Command {
    private final View view;
    private CourseDAO courseDAO;

    public UpdateCourseStatus(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "update_course_status";
    }

    @Override
    public void process() {
        view.write("Enter course title for update");
        String title = validate(view.read());
        Course ifExist = courseDAO.get(title);
        if (ifExist != null) {
            view.write("Enter update to course status");
            String value = validate(view.read());
            Optional<CourseStatus> tmp = CourseStatus.getCourseStatusValue(value.toUpperCase());
            CourseStatus status = tmp.isEmpty() ? null : tmp.get();
            if (status != null) {
                ifExist.setCourseStatus(status);
                courseDAO.update(ifExist);
                view.write(String.format("Course updated with status: %s", ifExist.getCourseStatus()));
            }else {
                view.write("Status not found!");
            }
        }else {
            view.write(String.format("Course with title=%s not exist!", title));
        }
    }

    private String validate(String value) {
        while (value.trim().isEmpty()){
            value = view.read();
        }
        return value.toUpperCase();
    }
}
