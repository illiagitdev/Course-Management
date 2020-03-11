package com.courses.management.course;

import com.courses.management.common.DatabaseConnector;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDAOImpl implements CourseDAO {
    private static final Logger LOG = LogManager.getLogger(CourseDAOImpl.class);
    private HikariDataSource dataSource = DatabaseConnector.getConnector();
    private static final String INSERT = "INSERT INTO course(title, status) VALUES(?, ?);";
    private static final String GET_ALL = "SELECT id, title, status FROM course;";
    private static final String UPDATE = "UPDATE course SET title = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM course WHERE id = ?;";
    private static final String GET_BY_ID = "SELECT id, title, status FROM course WHERE id = ?;";
    private static final String GET_BY_TITLE = "SELECT id, title, status FROM course WHERE title = ?;";

    @Override
    public void create(Course course) {
        LOG.debug(String.format("create: course.title=%s", course.getTitle()));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)){
            statement.setString(1, course.getTitle());
            statement.setString(2, course.getCourseStatus().getCourseStatus());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("Error creating course with title: %s", course.getTitle()), e);
        }
    }

    @Override
    public void update(Course course) {
        LOG.debug(String.format("Update: course.title=%s", course.getTitle()));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)){
            statement.setString(1, course.getTitle());
            statement.setInt(2, course.getId());
            int rows = statement.executeUpdate();
            if (rows == 0){
                LOG.warn(String.format("Course with id=%s not found", course.getId()));
            }
        } catch (SQLException e) {
            LOG.error(String.format("Error updating course with title: %s", course.getTitle()), e);
        }
    }

    @Override
    public void delete(int id) {
        LOG.debug(String.format("delete: course.id=%s", id));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setInt(1, id);
            int index = statement.executeUpdate();
            if (index == 0 ){
                LOG.warn(String.format("no course with id=%s found", id));
            }
        } catch (SQLException e) {
            LOG.error(String.format("Error deleting course with id: %s", id), e);
        }
    }

    @Override
    public Course get(int id) {
        Course course = null;
        LOG.debug(String.format("get(id): course.id=%s", id));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            course = buildCourse(resultSet);
        } catch (SQLException e) {
            LOG.error("Error retrieving course.", e);
        }
        return course;
    }

    @Override
    public List<Course> getAll() {
        List<Course> courses = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = statement.executeQuery();
            courses = new ArrayList<>();
            while (resultSet.next()){
                Course course = buildCourse(resultSet);
                courses.add(course);
            }
        } catch (SQLException e) {
            LOG.error("Error retrieving courses.", e);
        }
        return courses;
    }

    @Override
    public Course get(String title) {
        Course course = null;
        LOG.debug(String.format("get(title): course.title=%s", title));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_TITLE)){
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            course = buildCourse(resultSet);
        } catch (SQLException e) {
            LOG.error("Error retrieving course.", e);
        }
        return course;
    }

    @Override
    public void updateTitle(String title) {

    }

    private Course buildCourse(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getInt("id"));
        course.setTitle(resultSet.getString("title"));
        Optional<CourseStatus> status = CourseStatus.getCourseStatusValue(resultSet.getString("sex").toLowerCase());
        CourseStatus sex = status.isEmpty() ? null : status.get();
        course.setCourseStatus(sex);
        return course;
    }
}
