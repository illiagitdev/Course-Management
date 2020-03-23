package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.command.util.Commands;
import com.courses.management.common.command.util.InputString;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAO;
import com.courses.management.user.User;
import com.courses.management.user.UserDAO;
import com.courses.management.user.UserStatus;

import java.util.Objects;

public class UpdateUserCourse implements Command {
    private View view;
    private UserDAO userDAO;
    private static final int USER_EMAIL_INDEX = 1;
    private static final int COURSE_TITLE_INDEX = 2;
    private CourseDAO courseDAO;

    public UpdateUserCourse(View view, UserDAO dao) {
        this.view = view;
        this.userDAO = dao;
    }

    @Override
    public String command() {
        return Commands.UPDATE_USER_COURSE;
    }

    @Override
    public void process(InputString input) {
        String[] parameters = input.getParameters();
        String userEmail = parameters[USER_EMAIL_INDEX];
        String courseTitle = parameters[COURSE_TITLE_INDEX];
        User user = userDAO.get(userEmail);
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException(String.format("Can't find user by specified eMAIL = %s", userEmail));
        }
        Course course = courseDAO.get(courseTitle);
        if (Objects.isNull(course)) {
            throw new IllegalArgumentException(String.format("Can't find course by specified title = %s", courseTitle));
        }
        user.setCourse(course);
        user.setStatus(UserStatus.ACTIVE);
        userDAO.update(user);
        view.write("User successfully updated");
    }
}
