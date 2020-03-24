package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.command.util.Commands;
import com.courses.management.common.command.util.InputString;
import com.courses.management.user.User;
import com.courses.management.user.UserDAO;
import com.courses.management.user.UserStatus;

import java.util.Objects;

public class DeleteUserCourse implements Command {
    private View view;
    private UserDAO dao;
    private static final int EMAIL_INDEX = 1;


    public DeleteUserCourse(View view, UserDAO dao) {
        this.view = view;
        this.dao = dao;
    }

    @Override
    public String command() {
        return Commands.DELETE_USER_COURSE;
    }

    @Override
    public void process(InputString input) {
        String[] parameters = input.getParameters();
        String email = parameters[EMAIL_INDEX];
        User user = dao.get(email);
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("Can't find user with provided email");
        }
        user.setCourse(null);
        user.setStatus(UserStatus.NOT_ACTIVE);
        dao.update(user);
        view.write(String.format("User course removed and status set to %s", UserStatus.NOT_ACTIVE.getStatus()));
    }
}
