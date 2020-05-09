package com.courses.management.user;

import java.util.List;

public class UserHelper {
    public static final String FIRST_USER_NAME = "Barack";
    public static final String FIRST_USER_LAST_NAME = "Obama";
    public static final String FIRST_USER_EMAIL = "obama@mail.com";
    public static final String FIRST_USER_PASSWORD = "Jim";
    public static final String SECOND_USER_NAME = "pass";
    public static final String SECOND_USER_LAST_NAME = "Kerry";
    public static final String SECOND_USER_EMAIL = "kerry@mail.com";
    public static final String SECOND_USER_PASSWORD = "pass";
    public static final String INCORRECT_USER_EMAIL = "incirrect@mail.com";

    public static User createUser(String firstName, String lastName, String email, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserRole(UserRole.ROLE_NEWCOMER);
        user.setStatus(UserStatus.NOT_ACTIVE);
        return user;
    }

    public static List<User> prepareUserList() {
        return List.of(createUser(FIRST_USER_NAME, FIRST_USER_LAST_NAME, FIRST_USER_EMAIL, FIRST_USER_PASSWORD),
                createUser(SECOND_USER_NAME, SECOND_USER_LAST_NAME, SECOND_USER_EMAIL, SECOND_USER_PASSWORD));
    }
}
