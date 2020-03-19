package com.courses.management.common.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.command.util.InputString;

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
        String divide = "-".repeat(20);
        view.write(divide);
        view.write("-------------------HELP---------------------------------");
        view.write(divide);
        view.write("\t| create_course|title");
        view.write("\t|\t-> create course");
        view.write("");
        view.write("\t| exit");
        view.write("\t|\t-> exit application");
        view.write(divide);
    }
}
