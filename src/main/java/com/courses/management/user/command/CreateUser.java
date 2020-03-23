package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.command.util.Commands;
import com.courses.management.common.command.util.InputString;
import com.courses.management.user.User;
import com.courses.management.user.UserDAO;
import com.courses.management.user.Users;

import java.util.Objects;

public class CreateUser implements Command {
    private View view;
    private UserDAO dao;

    public CreateUser(View view, UserDAO dao) {
        this.view = view;
        this.dao = dao;
    }


    @Override
    public String command() {
        return Commands.CREATE_USER;
    }

    @Override
    public void process(InputString input) {
        User user = Users.mapUser(input);
        validateUser(user);
        dao.create(user);
        view.write(String.format("User %s %s created.", user.getFirstName(), user.getLastName()));
    }

    private void validateUser (User user) {
        if (user.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("User with first name cant be empty");
        }
        if (user.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("User with last name cant be empty");
        }
        if (Objects.nonNull(dao.get(user.getEmail()))) {
            throw new IllegalArgumentException(String.format("User with email %s already exists", user.getEmail()));
        }
    }
}
