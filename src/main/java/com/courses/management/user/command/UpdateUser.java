package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAO;
import com.courses.management.course.CourseDAOImpl;
import com.courses.management.user.*;

import java.util.Optional;

public class UpdateUser implements Command {
    private final View view;
    private UserDAO userDAO;

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
        User isExist = userDAO.get(id);
        if (isExist != null) {
            view.write("Enter updated user first name:");
            String firstName = validate(view.read());
            view.write("Enter updated user last name:");
            String lastName = validate(view.read());
            view.write("Enter updated user email:");
            String email = validate(view.read());
            view.write("Enter updated user status:");
            String status = validate(view.read());
            Optional<UserStatus> tmp = UserStatus.getUserStatus(status.toUpperCase());
            UserStatus userStatus = tmp.isEmpty() ? isExist.getStatus() : tmp.get();
            view.write("Enter updated user role:");
            String role = validate(view.read());
            Optional<UserRole> tmpRole = UserRole.getUserRole(role.toUpperCase());
            UserRole userRole = tmpRole.isEmpty() ? isExist.getUserRole() : tmpRole.get();
            view.write("Enter course title");
            String title = validate(view.read());
            Course course = (new CourseDAOImpl().get(title));
            User user = new User();
            user.setId(id);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setStatus(userStatus);
            user.setUserRole(userRole);
            user.setCourse(course);
            userDAO.update(user);
            view.write(String.format("User updated, id: %d", user.getId()));
        } else {
            view.write(String.format("User with id=%d not exist.", id));
        }
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
