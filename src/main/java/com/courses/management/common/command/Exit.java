package com.courses.management.common.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;

public class Exit implements Command {
    private View view;

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public String command() {
        return "exit";
    }

    @Override
    public void process() {
        view.write("Exit application");
        System.exit(0);
    }
}
