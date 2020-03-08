package com.courses.management.common;

import com.courses.management.common.command.Exit;
import com.courses.management.common.command.Help;
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
                new UpdateCourse(view),
                new GetAllCourses(view),
                new DeleteCourse(view),
                new GetCourseById(view),
                new GetCourseByTitle(view),

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
        for (Command command: commands){
            if (command.canProcess(input)){
                command.process();
                break;
            }
        }
    }
}
