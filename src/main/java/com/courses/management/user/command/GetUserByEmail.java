package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.user.User;
import com.courses.management.user.UserDAO;
import com.courses.management.user.UserDAOImpl;

public class GetUserByEmail implements Command {
    private final View view;
    private UserDAO userDAO;

    public GetUserByEmail(View view) {
        this.view = view;
        userDAO = new UserDAOImpl();
    }

    @Override
    public String command() {
        return "find_user_by_email";
    }

    @Override
    public void process() {
        view.write("Enter user email");
        String email = validate(view.read());
        User user = userDAO.getByEmail(email);
        if (user != null) {
            view.write(String.format("id=%d, (fn):'%s', (ln):)%s, email:%s, (role):%s, (status):%s",
                    user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                    user.getUserRole(), user.getStatus()));
        } else {
            view.write(String.format("User with email:'%s' not exist", email));
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
