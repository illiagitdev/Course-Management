package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.command.util.Commands;
import com.courses.management.common.command.util.InputString;
import com.courses.management.user.User;
import com.courses.management.user.UserDAO;
import com.courses.management.user.Users;

import java.util.Objects;

public class FindUser implements Command {
    private View view;
    private UserDAO dao;
    private final int EMAIL_INDEX = 1;

    public FindUser(View view, UserDAO dao) {
        this.view = view;
        this.dao = dao;
    }

    @Override
    public String command() {
        return Commands.FIND_USER;
    }

    @Override
    public void process(InputString input) {
        String email = input.getParameters()[EMAIL_INDEX];
        User user = dao.get(email);
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("Can't find user with provided email");
        }
        Users.printUser(view, user);
    }
}
