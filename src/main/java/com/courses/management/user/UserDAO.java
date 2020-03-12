package com.courses.management.user;

import com.courses.management.common.DataAccessObject;

import java.util.List;

public interface UserDAO extends DataAccessObject<User> {
    User getByEmail(String email);
    List<User> getByName(String firstName, String lastName);
    List<User> getByStatus(String status);
    List<User> getByCourseAndStatus(int courseId, String status);
}
