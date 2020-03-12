package com.courses.management.user.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAOImpl;
import com.courses.management.user.*;

public class CreateUser implements Command {
    private final View view;
    private UserDAO userDAO;

    public CreateUser(View view) {
        this.view = view;
        userDAO = new UserDAOImpl();
    }

    @Override
    public String command() {
        return "create_user";
    }

    @Override
    public void process() {
        User user = new User();
        view.write("Enter user first name:");
        String firstName = validate(view.read());
        user.setFirstName(firstName);
        view.write("Enter user last name:");
        String lastName = validate(view.read());
        user.setLastName(lastName);
        boolean invalidEmail = true;
        String email;
        while (invalidEmail) {
            view.write("Enter user email:");
            email = validate(view.read());
            User ifExist = userDAO.getByEmail(email);
            if (ifExist != null){
                invalidEmail = false;
                user.setEmail(email);
            }
        }
        user.setUserRole(UserRole.NEWCOMER);
        user.setStatus(UserStatus.ACTIVE);
        view.write("Enter user course title:");
        String title = validate(view.read());
        Course course = (new CourseDAOImpl()).get(title);
        user.setCourse(course);
        userDAO.create(user);
        view.write(String.format("User created, status: %s", user.getStatus()));
    }

    private String validate(String value) {
        while (value.trim().isEmpty()){
            view.write("Please enter the correct title!");
            value = view.read();
        }
        return value;
    }
}
