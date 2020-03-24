package com.courses.management.common;

import com.courses.management.common.command.Exit;
import com.courses.management.common.command.Help;
import com.courses.management.common.command.util.InputString;
import com.courses.management.common.exceptions.ExitExeption;
import com.courses.management.course.CourseDAOImpl;
import com.courses.management.course.command.*;
import com.courses.management.user.UserDAOImpl;
import com.courses.management.user.command.*;
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
        final CourseDAOImpl courseDAO = new CourseDAOImpl(dataSource);
        final UserDAOImpl userDAO = new UserDAOImpl(dataSource);
        this.commands = Arrays.asList(
                new Help(view),

                new CreateCourse(view, courseDAO),
                new FindCourse(view, courseDAO),
                new UpdateCourseTitle(view, courseDAO),
                new UpdateCourseStatus(view, courseDAO),
                new ShowCourses(view, courseDAO),
                new DeleteCourse(view, courseDAO),
                new CreateUser(view, userDAO),
                new FindUser(view, userDAO),
                new DeleteUserCourse(view, userDAO),
                new FindAllUsersByCourse(view, userDAO),
                new FindUsersByStatus(view, userDAO),
                new UpdateUserCourse(view, userDAO, courseDAO),
                new UpdateUserEmail(view, userDAO),

                new Exit(view)
        );
    }

    public void read(){
        view.write("Welcome!");
        view.write("Enter command or use 'help' to list all available commands!");
        try {
            doCommand();
        } catch (ExitExeption e) {
            /*NOP*/
        }
    }

    private void doCommand() {
        while (true) {
            InputString entry = new InputString(view.read());
            for (Command command : commands) {
                try {
                    if (command.canProcess(entry)) {
                        entry.validateParameters(command.command());
                        command.process(entry);
                        break;
                    }
                } catch (Exception e) {
                    if (e instanceof ExitExeption){
                        throw e;
                    }
                    LOG.warn(String.format("doCommand: WARN %s", e.getMessage()));
                    printError(e);
                    break;
                }
            }
        }
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        view.write("Error because of " + message);
        view.write("Please try again.");
    }
}
