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
    public void tearDown() {
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

    @Test
    public void testDeleteNotExistedCourseWithCorrectInputParameters () {
        //given
        in.add("delete_course|Title_Java");
        in.add("find_course|Title_Java");
        in.add("exit");

        //when
        mainController.read();

        //then
        assertEquals("Welcome!\n" +
                "Enter command or use 'help' to list all available commands!\n" +
                "Error because of Course with title Title_Java not exists\n" +
                "Please try again.\n" +
                "Error because of Can't find course with provided title.\n" +
                "Please try again.\n" +
                "Exit application\n", getData());
    }
    @Test

    public void testDeleteExistingCourseWithCorrectInputParameters () {
        //given
        in.add("create_course|Title_Java");
        in.add("delete_course|Title_Java");
        in.add("find_course|Title_Java");
        in.add("exit");

        //when
        mainController.read();

        //then
        assertEquals("Welcome!\n" +
                "Enter command or use 'help' to list all available commands!\n" +
                "Course created with title: Title_Java\n" +
                "Course status updated.\n" +
                "Course\n" +
                "\t title = Title_Java\n" +
                "\t status = DELETED\n" +
                "Exit application\n", getData());
    }

    @Test
    public void testShowCoursesWithEmptyDB () {
        //given
        in.add("show_courses");
        in.add("exit");

        //when
        mainController.read();

        //then
        assertEquals("Welcome!\n" +
                "Enter command or use 'help' to list all available commands!\n" +
                "Exit application\n", getData());
    }
    @Test
    public void testShowCoursesWithExistingCourses () {
        //given
        in.add("create_course|Java-1");
        in.add("create_course|Java-2");
        in.add("show_courses");
        in.add("exit");

        //when
        mainController.read();

        //then
        assertEquals("Welcome!\n" +
                "Enter command or use 'help' to list all available commands!\n" +
                "Course created with title: Java-1\n" +
                "Course created with title: Java-2\n" +
                "Course\n" +
                "\t title = Java-1\n" +
                "\t status = NOT_STARTED\n" +
                "Course\n" +
                "\t title = Java-2\n" +
                "\t status = NOT_STARTED\n" +
                "Exit application\n", getData());
    }

    @Test
    public void testUpdateCourseStatusWithCorrectParameters () {
        //given
        in.add("create_course|Java-test");
        in.add("update_course_status|Java-test|FINISHED");
        in.add("find_course|Java-test");
        in.add("exit");

        //when
        mainController.read();

        //then
        assertEquals("Welcome!\n" +
                "Enter command or use 'help' to list all available commands!\n" +
                "Course created with title: Java-test\n" +
                "Course status updated.\n" +
                "Course\n" +
                "\t title = Java-test\n" +
                "\t status = FINISHED\n" +
                "Exit application\n", getData());
    }

    @Test
    public void testUpdateCourseTitleWithCorrectParameters () {
        //given
        in.add("create_course|Java-oldTitle");
        in.add("update_course_title|Java-oldTitle|Java-newTitle");
        in.add("find_course|Java-newTitle");
        in.add("exit");

        //when
        mainController.read();

        //then
        assertEquals("Welcome!\n" +
                "Enter command or use 'help' to list all available commands!\n" +
                "Course created with title: Java-oldTitle\n" +
                "Course title updated.\n" +
                "Course\n" +
                "\t title = Java-newTitle\n" +
                "\t status = NOT_STARTED\n" +
                "Exit application\n", getData());
    }

    @Test
    public void testCreateUserWithCorrectInputParameters () {
        //given
        in.add("create_user|Fancy|Name|fancy.name@email.org");
        in.add("find_user|fancy.name@email.org");
        in.add("exit");

        //when
        mainController.read();

        //then
        assertEquals("Welcome!\n" +
                "Enter command or use 'help' to list all available commands!\n" +
                "User Fancy Name created.\n" +
                "User:\n" +
                "\t first name = Fancy\n" +
                "\t last name = Name\n" +
                "\t email = fancy.name@email.org\n" +
                "\t user role = NEWCOMER\n" +
                "\t user status = NOT_ACTIVE\n" +
                "Exit application\n", getData());
    }

    @Test
    public void testFindUsersByStatusWithCorrectInputParameters () {
        //given
        in.add("create_user|Fancy1|Name1|fancy1.name1@email.org");
        in.add("create_user|Fancy2|Name2|fancy2.name2@email.org");
        in.add("find_all_users_by_user_status|NOT_ACTIVE");
        in.add("exit");

        //when
        mainController.read();

        //then
        assertEquals("Welcome!\n" +
                "Enter command or use 'help' to list all available commands!\n" +
                "User Fancy1 Name1 created.\n" +
                "User Fancy2 Name2 created.\n" +
                "User:\n" +
                "\t first name = Fancy1\n" +
                "\t last name = Name1\n" +
                "\t email = fancy1.name1@email.org\n" +
                "\t user role = NEWCOMER\n" +
                "\t user status = NOT_ACTIVE\n" +
                "User:\n" +
                "\t first name = Fancy2\n" +
                "\t last name = Name2\n" +
                "\t email = fancy2.name2@email.org\n" +
                "\t user role = NEWCOMER\n" +
                "\t user status = NOT_ACTIVE\n" +
                "Exit application\n", getData());
    }

    @Test
    public void testUpdateUserEmailWithCorrectParameters () {
        //given
        in.add("create_user|Fancy|Name|fancy.name@email.org");
        in.add("update_user_email|fancy.name@email.org|new.fancy.name@email.org");
        in.add("find_user|new.fancy.name@email.org");
        in.add("exit");

        //when
        mainController.read();

        //then
        assertEquals("Welcome!\n" +
                "Enter command or use 'help' to list all available commands!\n" +
                "User Fancy Name created.\n" +
                "User updated successfully\n" +
                "User:\n" +
                "\t first name = Fancy\n" +
                "\t last name = Name\n" +
                "\t email = new.fancy.name@email.org\n" +
                "\t user role = NEWCOMER\n" +
                "\t user status = NOT_ACTIVE\n" +
                "Exit application\n", getData());
    }

    @Test
    public void testDeleteUserCourse () {
       //given
        in.add("create_user|Fancy|Name|fancy.name@email.org");
        in.add("delete_user_course|fancy.name@email.org");
        in.add("find_user|new.fancy.name@email.org");
        in.add("exit");

        //when
        mainController.read();

        //then
        assertEquals("Welcome!\n" +
                "Enter command or use 'help' to list all available commands!\n" +
                "User Fancy Name created.\n" +
                "User course removed and status set to NOT_ACTIVE\n" +
                "Error because of Can't find user with provided email\n" +
                "Please try again.\n" +
                "Exit application\n", getData());
    }

    @Test
    public void testUpdateUserCourseWithCorrectParameters () {
        //given
        in.add("create_user|Fancy|Name|fancy.name@email.org");
        in.add("create_course|Java-test");
        in.add("update_user_course|fancy.name@email.org|Java-test");//not executed
        in.add("find_user|fancy.name@email.org");
        in.add("exit");

        //when
        mainController.read();

        //then
        assertEquals("Welcome!\n" +
                "Enter command or use 'help' to list all available commands!\n" +
                "User Fancy Name created.\n" +
                "Course created with title: Java-test\n" +
                "User successfully updated\n" +
                "User:\n" +
                "\t first name = Fancy\n" +
                "\t last name = Name\n" +
                "\t email = fancy.name@email.org\n" +
                "\t user role = NEWCOMER\n" +
                "\t user status = ACTIVE\n" +
                "Exit application\n", getData());
    }
    @Test
    public void testFindAllUsersByCourseWithCorrectParameters () {
        //given
        in.add("create_course|Java-test");
        in.add("create_user|Fancy1|Name1|fancy1.name1@email.org");
        in.add("update_user_course|fancy1.name1@email.org|Java-test");//not executed
        in.add("create_user|Fancy2|Name2|fancy2.name2@email.org");
        in.add("update_user_course|fancy2.name2@email.org|Java-test");//not executed
        in.add("find_all_users_by_course_title|Java-test");
        in.add("exit");

        //when
        mainController.read();

        //then
        assertEquals("Welcome!\n" +
                "Enter command or use 'help' to list all available commands!\n" +
                "Course created with title: Java-test\n" +
                "User Fancy1 Name1 created.\n" +
                "User successfully updated\n" +
                "User Fancy2 Name2 created.\n" +
                "User successfully updated\n" +
                "User:\n" +
                "\t first name = Fancy1\n" +
                "\t last name = Name1\n" +
                "\t email = fancy1.name1@email.org\n" +
                "\t user role = NEWCOMER\n" +
                "\t user status = ACTIVE\n" +
                "User:\n" +
                "\t first name = Fancy2\n" +
                "\t last name = Name2\n" +
                "\t email = fancy2.name2@email.org\n" +
                "\t user role = NEWCOMER\n" +
                "\t user status = ACTIVE\n" +
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

    private void clearDatabase(){
        try (Connection connection = dbConnector.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP ALL OBJECTS;");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
