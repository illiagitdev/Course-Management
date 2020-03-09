package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.user.User;
import com.courses.management.user.UserDAOImpl;

public class GetUserById implements Command {
    private final View view;
    private DataAccessObject<User> userDAO;

    public GetUserById(View view) {
        this.view = view;
        userDAO = new UserDAOImpl();
    }

    @Override
    public String command() {
        return "user_by_id";
    }

    @Override
    public void process() {
        view.write("Enter user ID");
        int id = validateNumber(view.read());
        User user = userDAO.get(id);
        view.write(String.format("id=%d, (fn):'%s', (ln):)%s, email:%s, (role):%s, (status):%s",
                user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getUserRole(), user.getStatus()));
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
