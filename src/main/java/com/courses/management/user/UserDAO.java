package com.courses.management.user;

import com.courses.management.common.DataAccessObject;

import java.util.List;

public interface UserDAO extends DataAccessObject<User> {
    List<User> getByCourse(int courseId);
}
