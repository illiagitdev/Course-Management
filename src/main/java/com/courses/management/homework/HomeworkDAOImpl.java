package com.courses.management.homework;

import com.courses.management.common.DataAccessObject;
import com.courses.management.common.DatabaseConnector;
import com.courses.management.course.CourseDAOImpl;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeworkDAOImpl implements DataAccessObject<Homework>, HomeworkDAO {
    private static final Logger LOG = LogManager.getLogger(CourseDAOImpl.class);
    private HikariDataSource dataSource = DatabaseConnector.getConnector();
    private static final String INSERT = "INSERT INTO home_work(title, text, file_path) VALUES(?, ?, ?);";
    private static final String UPDATE = "UPDATE home_work SET title = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM home_work WHERE id = ?;";
    private static final String GET_BY_ID = "SELECT id, title, text, file_path FROM home_work WHERE id = ?;";
    private static final String GET_ALL = "SELECT id, title, text, file_path FROM home_work;";
    private static final String GET_BY_COURSE_ID = "SELECT id, title, text, file_path FROM home_work " +
            "WHERE course_id = ?;";

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
        Homework homework = null;
        LOG.debug(String.format("get(id): homework.id=%s", id));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            homework = new Homework();
            homework.setId(resultSet.getInt(1));
            homework.setTitle(resultSet.getString(2));
            homework.setText(resultSet.getString(3));
            homework.setPath(resultSet.getString(4));
        } catch (SQLException e) {
            LOG.error("Error retrieving course.", e);
        }
        return homework;
    }

    @Override
    public List<Homework> getAll() {
        List<Homework> homework = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = statement.executeQuery();
            homework = new ArrayList<>();
            while (resultSet.next()){
                Homework hw = new Homework();
                hw.setId(resultSet.getInt(1));
                hw.setTitle(resultSet.getString(2));
                hw.setText(resultSet.getString(3));
                hw.setPath(resultSet.getString(4));
                homework.add(hw);
            }
        } catch (SQLException e) {
            LOG.error("Error retrieving courses.", e);
        }
        return homework;
    }

    @Override
    public List<Homework> getAll(int courseId) {
        LOG.debug(String.format("getAll(courseId): homework.courseId=%s", courseId));
        List<Homework> homework = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_COURSE_ID)){
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            homework = new ArrayList<>();
            while (resultSet.next()){
                Homework hw = new Homework();
                hw.setId(resultSet.getInt(1));
                hw.setTitle(resultSet.getString(2));
                hw.setText(resultSet.getString(3));
                hw.setPath(resultSet.getString(4));
                homework.add(hw);
            }
        } catch (SQLException e) {
            LOG.error("Error retrieving courses.", e);
        }
        return homework;
    }
}
