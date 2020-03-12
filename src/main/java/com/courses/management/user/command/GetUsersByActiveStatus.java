package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.user.User;
import com.courses.management.user.UserDAO;
import com.courses.management.user.UserDAOImpl;
import com.courses.management.user.UserStatus;

import java.util.List;
import java.util.Optional;

public class GetUsersByActiveStatus implements Command {
    private final View view;
    private UserDAO userDAO;

    public GetUsersByActiveStatus(View view) {
        this.view = view;
        userDAO = new UserDAOImpl();
    }

    @Override
    public String command() {
        return "get_all_users_by_course_active_status";
    }

    @Override
    public void process() {
        view.write("Enter user status");
        String value = validate(view.read());
        Optional<UserStatus> tmp = UserStatus.getUserStatus(value.toUpperCase());
        UserStatus userStatus = tmp.isEmpty() ? null : tmp.get();
        if (userStatus != null) {
            List<User> users = userDAO.getByStatus(userStatus.getStatus());
            view.write(String.format("Users with userStatus=%s", userStatus));
            for (User user: users) {
                view.write(String.format("id=%d, (fn):'%s', (ln):)%s, email:%s, (role):%s, (status):%s",
                        user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                        user.getUserRole(), user.getStatus()));
            }
        } else {
            view.write(String.format("Users with userStatus=%s not found.", value));
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
