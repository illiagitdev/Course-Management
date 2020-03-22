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
        validateIfEmailExists(user.getEmail());
        dao.create(user);
        view.write("User created.");
    }

    private void validateIfEmailExists(String email) {
        if (Objects.nonNull(dao.get(email))) {
            throw new IllegalArgumentException(String.format("User with email %s already exists", email));
        }
    }
}
