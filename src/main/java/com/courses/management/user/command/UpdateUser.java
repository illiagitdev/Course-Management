package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.user.User;
import com.courses.management.user.UserDAOImpl;

public class UpdateUser implements Command {
    private final View view;
    private DataAccessObject<User> userDAO;

    public UpdateUser(View view) {
        this.view = view;
        userDAO = new UserDAOImpl();
    }

    @Override
    public String command() {
        return "update_user";
    }

    @Override
    public void process() {
        view.write("Enter user ID for update");
        int id = validateNumber(view.read());
        view.write("Enter updated user first name:");
        String firstName = validate(view.read());
        view.write("Enter updated user last name:");
        String lastName = validate(view.read());
        view.write("Enter updated user email:");
        String email = validate(view.read());
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userDAO.update(user);
        view.write(String.format("User updated, id: %d", user.getId()));
    }

    private String validate(String value) {
        while (value.trim().isEmpty()){
            view.write("Please enter the correct title!");
            value = view.read();
        }
        return value;
    }

    private int validateNumber(String value) {
        int res = 0;
        try {
            res = Integer.parseInt(value);
        } catch (RuntimeException e) {
            view.write("Input not a number!");
        }
        return res;
    }
}
