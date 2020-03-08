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
    private static final String UPDATE = "UPDATE home_work SET title = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM home_work WHERE id = ?;";

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
        LOG.info(String.format("update: homework.title=%s", homework.getTitle()));
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, homework.getTitle());
            statement.setString(2, homework.getText());
            statement.setString(3, homework.getPath());
            statement.setInt(4, homework.getId());
            int row = statement.executeUpdate();
            if (row == 0){
                LOG.warn(String.format("Homework with id=%s not found", homework.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        LOG.debug(String.format("delete: homework.id=%s", id));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setInt(1, id);
            int index = statement.executeUpdate();
            if (index == 0 ){
                LOG.warn(String.format("no homework with id=%s found", id));
            }
        } catch (SQLException e) {
            LOG.error(String.format("Error deleting homework with id: %s", id), e);
        }
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
