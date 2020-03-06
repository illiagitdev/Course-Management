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
        view.write("--------------------------------------");
        view.write("-------------HELP---------------------");
        view.write("--------------------------------------");
        view.write("Command         |   Description-------");
        view.write("");
        view.write("create_course   |   create course-----");
        view.write("exit            |   exit application--");
        view.write("--------------------------------------");
    }
}
