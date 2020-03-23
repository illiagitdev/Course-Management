package com.courses.management;

import com.courses.management.common.Command;
import com.courses.management.common.DatabaseConnectorTest;
import com.courses.management.common.View;
import com.courses.management.common.command.util.InputString;
import com.courses.management.user.UserDAO;
import com.courses.management.user.UserDAOImpl;
import com.courses.management.user.command.CreateUser;
import com.courses.management.user.command.FindUser;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class IntegrationTest {
    private DatabaseConnectorTest dbConnector = new DatabaseConnectorTest();
    private View view = mock(View.class);
    private UserDAO userDAO = new UserDAOImpl(dbConnector.getDataSource());
    private Command createUserCommand = new CreateUser(view, userDAO);
    private Command findUserCommand = new FindUser(view, userDAO);

    @Test
    public void testCreateUser() {
        InputString inputString = new InputString("create_users|Illia|Bondarchuk|illia.bondarchuk@email.com");
        createUserCommand.process(inputString);
        findUserCommand.process(new InputString("find_user|illia.bondarchuk@email.com"));
    }
}
