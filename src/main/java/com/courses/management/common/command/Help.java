package com.courses.management.common.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.command.util.InputString;
import com.courses.management.course.CourseStatus;

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

        view.write(String.format("\t| %s", EXIT));
        view.write("\t|\t-> exit application");
        view.write(divide);
    }

    private String collectCourseStatus() {
        return Arrays.stream(CourseStatus.values()).
                map(CourseStatus::getCourseStatus).collect(Collectors.joining(", "));
    }
}
