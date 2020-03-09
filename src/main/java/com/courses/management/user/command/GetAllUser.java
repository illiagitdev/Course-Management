package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.user.User;
import com.courses.management.user.UserDAOImpl;

import java.util.List;

public class GetAllUser implements Command {
    private final View view;
    private DataAccessObject<User> userDAO;

    public GetAllUser(View view) {
        this.view = view;
        userDAO = new UserDAOImpl();
    }

    @Override
    public String command() {
        return "all_users";
    }

    @Override
    public void process() {
        List<User> users = userDAO.getAll();
        view.write("Users");
        for (User user: users) {
            view.write(String.format("id=%d, (fn):'%s', (ln):)%s, email:%s, (role):%s, (status):%s",
                    user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                    user.getUserRole(), user.getStatus()));
        }
    }
}
