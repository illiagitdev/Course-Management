package com.courses.management.course;

import com.courses.management.common.DataAccessObject;

public interface CourseDAO extends DataAccessObject<Course> {
    Course get(String title);
    void updateTitle(Course course);
    void updateStatus(Course course);
    void delete(String title);
}
