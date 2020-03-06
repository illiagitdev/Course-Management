package com.courses.management.course;

import com.courses.management.common.DataAccessObject;
import com.courses.management.common.DatabaseConnector;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseDAO extends DataAccessObject<Course> {
    private static final Logger LOG = LogManager.getLogger(CourseDAO.class);
    private static final String INSERT = "INSERT INTO course(title, status) VALUES(?, ?);";
    private HikariDataSource dataSource = DatabaseConnector.getConnector();

    @Override
    public void create(Course course) {
        LOG.debug(String.format("create: course.title=%s", course.getTitle()));
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT)){
            statement.setString(1, course.getTitle());
            statement.setString(2, course.getCourseStatus().getCourseStatus());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("Error code: %s\nState: %s\nError massage: %s",
                    e.getErrorCode(), e.getSQLState(), e.getMessage()));
        }
    }
}
