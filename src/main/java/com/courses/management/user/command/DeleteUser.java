package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.user.User;
import com.courses.management.user.UserDAOImpl;

public class DeleteUser implements Command {
    private final View view;
    private DataAccessObject<User> userDAO;

    public DeleteUser(View view) {
        this.view = view;
        userDAO = new UserDAOImpl();
    }

    @Override
    public String command() {
        return "delete_user";
    }

    @Override
    public void process() {
        view.write("Enter user ID for delete");
        int id = validateNumber(view.read());
        userDAO.delete(id);
        view.write(String.format("User deleted, id: %d", id));
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
