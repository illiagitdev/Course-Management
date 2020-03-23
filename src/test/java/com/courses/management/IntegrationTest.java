package com.courses.management;

import com.courses.management.common.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static junit.framework.TestCase.assertEquals;

public class IntegrationTest {
    private DatabaseConnectorTest dbConnector;
    private MainController mainController;
    private CustomImputStream in;
    private ByteArrayOutputStream out;

    @Before
    public void setup() {
        this.dbConnector = new DatabaseConnectorTest();
        this.mainController = new MainController(new Console(), dbConnector.getDataSource());
        this.in = new CustomImputStream();
        this.out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @After
    public void tearDown() throws SQLException {
        clearDatabase();
    }

    @Test
    public void testHelpCommand () {
        //given
        in.add("help");
        in.add("exit");

        //when
        mainController.read();

        //then
        assertEquals("Welcome!\n" +
                "Enter command or use 'help' to list all available commands!\n" +
                "--------------------------------------------------\n" +
                "-------------------HELP---------------------------------\n" +
                "--------------------------------------------------\n" +
                "\t| create_course|title\n" +
                "\t|\t-> create course\n" +
                "\n" +
                "\t| find_course|title\n" +
                "\t|\t-> find course by title\n" +
                "\n" +
                "\t| update_course_title|oldTitle|newTitle\n" +
                "\t|\t-> update course by title. Title is a unique field.\n" +
                "\n" +
                "\t| update_course_status|title|status\n" +
                "\t|\t-> update course by status. Possible values: NOT_STARTED, IN_PROGRESS, FINISHED, DELETED\n" +
                "\n" +
                "\t| show_courses\n" +
                "\t|\t-> show all course\n" +
                "\n" +
                "\t| delete_course|title\n" +
                "\t|\t-> move course to DELETED status\n" +
                "\n" +
                "\t| create_user|firstName|lastName|email\n" +
                "\t|\t-> create user, user role will be set as NEWCOMER\n" +
                "\t|\t-> and user status asNOT_ACTIVE\n" +
                "\n" +
                "\t| find_user|email\n" +
                "\t|\t-> find user by email\n" +
                "\n" +
                "\t| delete_user_course|email\n" +
                "\t|\t-> delete user course and set user to NOT_ACTIVE status\n" +
                "\n" +
                "\t| find_all_users_by_course_title|courseTitle\n" +
                "\t|\t-> get all user by specified course\n" +
                "\n" +
                "\t| find_all_users_by_user_status|status\n" +
                "\t|\t-> get all users by specified status [ACTIVE, NOT_ACTIVE]\n" +
                "\n" +
                "\t| update_user_course|userEmail|courseTitle\n" +
                "\t|\t-> update users course by specified course title\n" +
                "\n" +
                "\t| update_user_email|oldEmail|newEmail\n" +
                "\t|\t-> update users email\n" +
                "\n" +
                "\t| exit\n" +
                "\t|\t-> exit application\n" +
                "--------------------------------------------------\n" +
                "Exit application\n", getData());
    }

    @Test
    public void testCreateCourseWithCorrectInputParameters () {
        //given
        in.add("create_course|Title_Java");
        in.add("find_course|Title_Java");
        in.add("exit");

        //when
        mainController.read();

        //then
        assertEquals("Welcome!\n" +
                "Enter command or use 'help' to list all available commands!\n" +
                "Course created with title: Title_Java\n" +
                "Course\n" +
                "\t title = Title_Java\n" +
                "\t status = NOT_STARTED\n" +
                "Exit application\n", getData());
    }

    private String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8").replaceAll("\r\n", "\n");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    private void clearDatabase() throws SQLException {
        try (Connection connection = dbConnector.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {

        }
    }
}
