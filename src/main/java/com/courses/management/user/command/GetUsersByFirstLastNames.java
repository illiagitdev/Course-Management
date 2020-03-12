package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.user.User;
import com.courses.management.user.UserDAO;
import com.courses.management.user.UserDAOImpl;

import java.util.List;

public class GetUsersByFirstLastNames implements Command {
    private final View view;
    private UserDAO userDAO;

    public GetUsersByFirstLastNames(View view) {
        this.view = view;
        userDAO = new UserDAOImpl();
    }

    @Override
    public String command() {
        return "find_users_by_firstname_and_lastname ";
    }

    @Override
    public void process() {
        view.write("Enter user first name");
        String firstName = validate(view.read());
        view.write("Enter user last name");
        String lastName = validate(view.read());
        List<User> users = userDAO.getByName(firstName, lastName);
        if (users != null){
            view.write(String.format("Users by first=%s and last=%s names", firstName, lastName));
            for (User user: users) {
                view.write(String.format("id=%d, (fn):'%s', (ln):)%s, email:%s, (role):%s, (status):%s",
                        user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                        user.getUserRole(), user.getStatus()));
            }
        } else {
            view.write(String.format("Users with first=%s and last=%s names", firstName, lastName));
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
