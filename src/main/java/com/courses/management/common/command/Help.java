package com.courses.management.common.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.command.util.InputString;
import com.courses.management.course.CourseStatus;
import com.courses.management.user.UserRole;
import com.courses.management.user.UserStatus;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.courses.management.common.command.util.Commands.*;

public class Help implements Command {
    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public String command() {
        return HELP;
    }

    @Override
    public void process(InputString input) {
        String divide = "-".repeat(50);
        view.write(divide);
        view.write("-------------------HELP---------------------------------");
        view.write(divide);
        view.write(String.format("\t| %s", CREATE_COURSE));
        view.write("\t|\t-> create course");
        view.write("");

        view.write(String.format("\t| %s", FIND_COURSE));
        view.write("\t|\t-> find course by title");
        view.write("");

        view.write(String.format("\t| %s", UPDATE_COURSE_TITLE));
        view.write("\t|\t-> update course by title. Title is a unique field.");
        view.write("");

        view.write(String.format("\t| %s", UPDATE_COURSE_STATUS));
        view.write("\t|\t-> update course by status. Possible values: " + collectCourseStatus());
        view.write("");

        view.write(String.format("\t| %s", SHOW_COURSES));
        view.write("\t|\t-> show all course");
        view.write("");

        view.write(String.format("\t| %s", DELETE_COURSE));
        view.write("\t|\t-> move course to DELETED status");
        view.write("");

        view.write(String.format("\t| %s", CREATE_USER));
        view.write("\t|\t-> create user, user role will be set as " + UserRole.NEWCOMER);
        view.write("\t|\t-> and user status as" + UserStatus.NOT_ACTIVE);
        view.write("");

        view.write(String.format("\t| %s", FIND_USER));
        view.write("\t|\t-> find user by email");
        view.write("");

        view.write(String.format("\t| %s", DELETE_USER_COURSE));
        view.write("\t|\t-> delete user course and set user to NOT_ACTIVE status");
        view.write("");

        view.write(String.format("\t| %s", FIND_ALL_USERS_BY_COURSE));
        view.write("\t|\t-> get all user by specified course");
        view.write("");

        view.write(String.format("\t| %s", FIND_ALL_USERS_BY_STATUS));
        view.write("\t|\t-> get all users by specified status [" + collectUsersStatuses() + "]");
        view.write("");

        view.write(String.format("\t| %s", UPDATE_USER_COURSE));
        view.write("\t|\t-> update users course by specified course title");
        view.write("");

        view.write(String.format("\t| %s", UPDATE_USER_EMAIL));
        view.write("\t|\t-> update users email");
        view.write("");

        view.write(String.format("\t| %s", EXIT));
        view.write("\t|\t-> exit application");
        view.write(divide);
    }

    private String collectUsersStatuses() {
        return Arrays.stream(UserStatus.values()).
                map(UserStatus::getStatus).collect(Collectors.joining(", "));
    }

    private String collectCourseStatus() {
        return Arrays.stream(CourseStatus.values()).
                map(CourseStatus::getCourseStatus).collect(Collectors.joining(", "));
    }
}
