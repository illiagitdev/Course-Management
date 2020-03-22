package com.courses.management.common;

import com.courses.management.common.command.Exit;
import com.courses.management.common.command.Help;
import com.courses.management.common.command.util.InputString;
import com.courses.management.course.CourseDAOImpl;
import com.courses.management.course.command.*;
import com.courses.management.user.UserDAOImpl;
import com.courses.management.user.command.CreateUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

public class MainController {
    private static final Logger LOG = LogManager.getLogger(MainController.class);
    private View view;
    private List<Command> commands;

    public MainController(View view, DataSource dataSource) {
        this.view = view;
        this.commands = Arrays.asList(
                new Help(view),

                new CreateCourse(view, new CourseDAOImpl(dataSource)),
                new FindCourse(view, new CourseDAOImpl(dataSource)),
                new UpdateCourseTitle(view, new CourseDAOImpl(dataSource)),
                new UpdateCourseStatus(view, new CourseDAOImpl(dataSource)),
                new ShowCourses(view, new CourseDAOImpl(dataSource)),
                new DeleteCourse(view, new CourseDAOImpl(dataSource)),
                new CreateUser(view, new UserDAOImpl(dataSource)),

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
                    entry.validateParameters(command.command());
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
        view.write("Please try again.");
    }
}
