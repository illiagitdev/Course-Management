package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAO;
import com.courses.management.course.CourseDAOImpl;

public class UpdateCourseTitle implements Command {
    private final View view;
    private CourseDAO courseDAO;

    public UpdateCourseTitle(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "update_course_title";
    }

    @Override
    public void process() {
        view.write("Enter course title for update of title");
        String title = validate(view.read());
        Course ifExist = courseDAO.get(title);
        if (ifExist != null) {
            view.write("Enter update to course title");
            String newTitle = validate(view.read());
            ifExist.setTitle(newTitle);
            courseDAO.updateTitle(ifExist);
            view.write(String.format("Course updated with title: %s", newTitle));
        }else {
            view.write(String.format("Course with title=%s not exist!", title));
        }

    }

    private String validate(String value) {
        while (value.trim().isEmpty()){
            value = view.read();
        }
        return value;
    }
}
