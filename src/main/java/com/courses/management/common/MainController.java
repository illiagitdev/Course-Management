package com.courses.management.common;

import com.courses.management.common.command.Exit;
import com.courses.management.common.command.Help;
import com.courses.management.common.command.util.InputString;
import com.courses.management.course.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class MainController {
    private static final Logger LOG = LogManager.getLogger(MainController.class);
    private View view;
    private List<Command> commands;

    public MainController(View view) {
        this.view = view;
        this.commands = Arrays.asList(
                new Help(view),

                new CreateCourse(view),

                new Exit(view)
        );
    }

    public void read(){
        view.write("Welcome!");
        while (true){
            view.write("Enter command or 'help' for details!");
            String read = view.read();
            doCommand(read);
        }
    }

    private void doCommand(String input) {
        LOG.debug(String.format("doCommand: input=%s", input));
        InputString entry = new InputString(input);
        for (Command command: commands){
            try {
                if (command.canProcess(entry)){
                    command.process(entry);
                    break;
                }
            } catch (Exception e) {
                LOG.warn(String.format("doCommand: WARN %s", e.getMessage()));
                printError(e);
                break;
            }
        }
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        view.write("Error because of " + message);
    }
}
