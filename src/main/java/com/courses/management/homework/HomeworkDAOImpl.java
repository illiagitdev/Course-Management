package com.courses.management.homework;

import com.courses.management.common.DataAccessObject;
import com.courses.management.common.DatabaseConnector;
import com.courses.management.course.CourseDAOImpl;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class HomeworkDAOImpl implements DataAccessObject<Homework>, HomeworkDAO {
    private static final Logger LOG = LogManager.getLogger(CourseDAOImpl.class);
    private HikariDataSource dataSource = DatabaseConnector.getConnector();
    private static final String INSERT = "INSERT INTO home_work(title, text, file_path) VALUES(?, ?, ?);";

    @Override
    public void create(Homework homework) {
        LOG.info(String.format("create: homework.title=%s", homework.getTitle()));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)){
            statement.setString(1, homework.getTitle());
            statement.setString(2, homework.getText());
            statement.setString(3, homework.getPath());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("Error creating homework with title=%s", homework.getTitle()), e);
        }
    }

    @Override
    public void update(Homework homework) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Homework get(int id) {
        return null;
    }

    @Override
    public List<Homework> getAll() {
        return null;
    }

    @Override
    public List<Homework> getAll(int courseId) {
        return null;
    }
}
