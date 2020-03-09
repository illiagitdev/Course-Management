package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.user.User;
import com.courses.management.user.UserDAO;
import com.courses.management.user.UserDAOImpl;

import java.util.List;

public class GetCourseUsers implements Command {
    private final View view;
    private UserDAO userDAO;

    public GetCourseUsers(View view) {
        this.view = view;
        userDAO = new UserDAOImpl();
    }

    @Override
    public String command() {
        return "users_by_course_id";
    }

    @Override
    public void process() {
        view.write("Enter course ID");
        int id = validateNumber(view.read());
        List<User> users = userDAO.getByCourse(id);
        view.write(String.format("Users by course id=%d", id));
        for (User user: users) {
            view.write(String.format("id=%d, (fn):'%s', (ln):)%s, email:%s, (role):%s, (status):%s",
                    user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                    user.getUserRole(), user.getStatus()));
        }
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
