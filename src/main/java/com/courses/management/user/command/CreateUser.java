package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.user.*;

public class CreateUser implements Command {
    private final View view;
    private UserDAO userDAO;

    public CreateUser(View view) {
        this.view = view;
        userDAO = new UserDAOImpl();
    }

    @Override
    public String command() {
        return "create_user";
    }

    @Override
    public void process() {
        view.write("Enter user first name:");
        String firstName = validate(view.read());
        view.write("Enter user last name:");
        String lastName = validate(view.read());
        view.write("Enter user email:");
        String email = validate(view.read());
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUserRole(UserRole.NEWCOMER);
        user.setStatus(UserStatus.NOT_ACTIVE);
        userDAO.create(user);
        view.write(String.format("User created, status: %s", user.getStatus()));
    }

    private String validate(String value) {
        while (value.trim().isEmpty()){
            view.write("Please enter the correct title!");
            value = view.read();
        }
        return value;
    }
}
