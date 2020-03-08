package com.courses.management.common.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;

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
    public void process() {
        view.write("--------------------------------------------------------");
        view.write("-------------------HELP---------------------------------");
        view.write("--------------------------------------------------------");
        view.write("    Command         |   Description---------------------");
        view.write("");
        view.write("    create_course   |   create course-------------------");
        view.write("    update_course   |   Update existing course----------");
        view.write("    course_by_id    |   get course by id----------------");
        view.write("    get_all_courses |   returns all courses ------------");
        view.write("    delete_course   |   delete course by indicated ID---");
        view.write("    course_by_title |   get course by title-------------");
        view.write("");
        view.write("    Command         |   Description-------");
        view.write("    Command         |   Description-------");
        view.write("    Command         |   Description-------");
        view.write("    Command         |   Description-------");
        view.write("    Command         |   Description-------");
        view.write("");
        view.write("");
        view.write("");
        view.write("    exit            |   exit application----------------");
        view.write("--------------------------------------------------------");
    }
}