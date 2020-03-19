package com.courses.management.common.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.command.util.InputString;
import com.courses.management.course.CourseStatus;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Help implements Command {
    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public String command() {
        return "help";
    }

    @Override
    public void process(InputString input) {
        String divide = "-".repeat(50);
        view.write(divide);
        view.write("-------------------HELP---------------------------------");
        view.write(divide);
        view.write("\t| create_course|title");
        view.write("\t|\t-> create course");
        view.write("");

        view.write("\t| find_course|title");
        view.write("\t|\t-> find course by title");
        view.write("");

        view.write("\t| update_course_title|oldTitle|newTitle");
        view.write("\t|\t-> update course by title. Title is a unique field.");
        view.write("");

        view.write("\t| update_course_status|title|status");
        view.write("\t|\t-> update course by status. Possible values: " + collectCourseStatus());
        view.write("");

        view.write("\t| show_courses");
        view.write("\t|\t-> show all course");
        view.write("");

        view.write("\t| delete_course|title");
        view.write("\t|\t-> move course to DELETED status");
        view.write("");

        view.write("\t| exit");
        view.write("\t|\t-> exit application");
        view.write(divide);
    }

    private String collectCourseStatus() {
        return Arrays.stream(CourseStatus.values()).
                map(CourseStatus::getCourseStatus).collect(Collectors.joining(", "));
    }
}
