package com.courses.management.user;

import com.courses.management.common.DataAccessObject;

import java.util.List;

public interface UserDAO extends DataAccessObject<User> {
    User get(String email);
    void removeUserCourseAndSetStatus (String email, UserStatus status);
    List<User> getUsersByCourse (String courseTitle);
    List<User> getAllByStatus (UserStatus status);
}
