package com.courses.management.user;

import java.util.List;

public interface UserDAO {
    List<User> getByCourse(int courseId);
}
