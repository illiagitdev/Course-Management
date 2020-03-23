package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.command.util.Commands;
import com.courses.management.common.command.util.InputString;
import com.courses.management.user.User;
import com.courses.management.user.UserDAO;
import com.courses.management.user.Users;

import java.util.List;

public class FindAllUsersByCourse implements Command {
    private View view;
    private UserDAO dao;
    private static final int COURSE_TITLE_INDEX = 1;

    public FindAllUsersByCourse(View view, UserDAO dao) {
        this.view = view;
        this.dao = dao;
    }

    @Override
    public String command() {
        return Commands.FIND_ALL_USERS_BY_COURSE;
    }

    @Override
    public void process(InputString input) {
        String[] parameters = input.getParameters();
        String courseTitle = parameters[COURSE_TITLE_INDEX];
        List<User> users = dao.getUsersByCourse(courseTitle);
        if (users.isEmpty()) {
            view.write("There is no users by specified title");
        } else {
            users.forEach(user -> Users.printUser(view, user));
        }
    }
}
