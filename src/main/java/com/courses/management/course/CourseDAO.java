package com.courses.management.course;

import com.courses.management.common.DataAccessObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseDAO extends DataAccessObject<Course> {
    private static final Logger LOG = LogManager.getLogger(CourseDAO.class);
    private static final String INSERT = "INSERT INTO course(title, status) VALUES(?, ?);";

    public CourseDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void create(Course course) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT)){
            statement.setString(1, course.getTitle());
            statement.setString(2, course.getCourseStatus().getCourseStatus());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("", ));
            e.printStackTrace();
        }
    }
}
