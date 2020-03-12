package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.user.User;
import com.courses.management.user.UserDAO;
import com.courses.management.user.UserDAOImpl;

public class DeleteUserByEmail implements Command {
    private final View view;
    private UserDAO userDAO;

    public DeleteUserByEmail(View view) {
        this.view = view;
        userDAO = new UserDAOImpl();
    }

    @Override
    public String command() {
        return "delete_user_by_email";
    }

    @Override
    public void process() {
        view.write("Enter user email for delete");
        String email = validate(view.read());
        User isExist = userDAO.getByEmail(email);
        if (isExist != null){
            userDAO.deleteByEmail(email);
            view.write(String.format("User deleted, email: %s", email));
        } else {
            view.write(String.format("User with email=%s not exist.", email));
        }
    }

    private String validate(String value) {
        while (value.trim().isEmpty()){
            view.write("Please enter the correct title!");
            value = view.read();
        }
        return value;
    }
}
