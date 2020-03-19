package com.courses.management.course;

import com.courses.management.common.DatabaseConnector;
import com.courses.management.common.exceptions.SQLCourseException;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {
    private static final Logger LOG = LogManager.getLogger(CourseDAOImpl.class);
    private HikariDataSource dataSource = DatabaseConnector.getConnector();
    private static final String INSERT = "INSERT INTO course(title, status) VALUES(?, ?);";
    private static final String FIND_COURSE_BY_TITLE = "SELECT id, title, status FROM course WHERE title = ?;";
    private static final String UPDATE_COURSE = "UPDATE course SET title = ?, status = ? WHERE id = ?;";
    private static final String FIND_COURSE_BY_ID = "SELECT id, title, status FROM course WHERE id = ?;";
    private static final String FIND_ALL_COURSES = "SELECT id, title, status FROM course;";
    private static final String GET_ALL_STATUS = "SELECT id, title, status FROM course WHERE status = ?;";
    private static final String UPDATE_TITLE = "UPDATE course SET title = ? WHERE id = ?;";
    private static final String UPDATE_STATUS = "UPDATE course SET status = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM course WHERE id = ?;";
    private static final String DELETE_TITLE = "DELETE FROM course WHERE title = ?;";

    @Override
    public void create(Course course) {
        LOG.debug(String.format("create: course.title=%s", course.getTitle()));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, course.getTitle());
            statement.setString(2, course.getCourseStatus().getCourseStatus());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("Error creating course with title: %s", course.getTitle()), e);
            throw new SQLCourseException("Error occurred when saving a course.");
        }
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Course can't be deleted.");
    }

//    @Override
//    public void delete(String title) {
//        LOG.debug(String.format("delete: course.title=%s", title));
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(DELETE_TITLE)){
//            statement.setString(1, title);
//            int index = statement.executeUpdate();
//            if (index == 0 ){
//                LOG.warn(String.format("no course with title=%s found", title));
//            }
//        } catch (SQLException e) {
//            LOG.error(String.format("Error deleting course with title: %s", title), e);
//        }
//    }

    @Override
    public Course get(int id) {
        LOG.debug(String.format("get: course.id=%s", id));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_COURSE_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return getCourse(resultSet);
        } catch (SQLException e) {
            LOG.error(String.format("get: course.id=%s", id), e);
            throw new SQLCourseException("Error occurred when find a course.");
        }
    }

    @Override
    public List<Course> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_COURSES)) {
            ResultSet resultSet = statement.executeQuery();
            return getCourseList(resultSet);
        } catch (SQLException e) {
            LOG.error("getAll", e);
            throw new SQLCourseException("Error occurred when find all courses.");
        }
    }


    //    @Override
//    public List<Course> getAll(String status) {
//        LOG.debug(String.format("getAll: status=%s", status));
//        List<Course> courses = null;
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(GET_ALL_STATUS)){
//            statement.setString(1, status);
//            ResultSet resultSet = statement.executeQuery();
//            courses = new ArrayList<>();
//            while (resultSet.next()){
//                Course course = buildCourse(resultSet);
//                courses.add(course);
//            }
//        } catch (SQLException e) {
//            LOG.error("Error retrieving courses.", e);
//        }
//        return courses;
//    }

    @Override
    public Course get(String title) {
        LOG.debug(String.format("debug:get: course.title=%s", title));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_COURSE_BY_TITLE)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            return getCourse(resultSet);
        } catch (SQLException e) {
            LOG.error(String.format("error:get: course.title=%s", title), e);
            throw new SQLCourseException("Error occurred when find a course.");
        }
    }

    @Override
    public void update(Course course) {
        LOG.debug(String.format("update: course.title=%s, course.status=%s", course.getTitle(),
                course.getCourseStatus()));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_COURSE)) {
            statement.setString(1, course.getTitle());
            statement.setString(2, course.getCourseStatus().getCourseStatus());
            statement.setInt(3, course.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(String.format("Error updating course with title: %s", course.getTitle()), e);
            throw new SQLCourseException("Error occurred when update a course.");
        }
    }


//    @Override
//    public void updateTitle(Course course) {
//        LOG.debug(String.format("Update: course.title=%s", course.getTitle()));
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(UPDATE_TITLE)){
//            statement.setString(1, course.getTitle());
//            statement.setInt(2, course.getId());
//            statement.execute();
//        } catch (SQLException e) {
//            LOG.error(String.format("Error updating course with title: %s", course.getTitle()), e);
//        }
//    }
//
//    @Override
//    public void updateStatus(Course course) {
//        LOG.debug(String.format("Update: course.status=%s", course.getCourseStatus()));
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS)){
//            statement.setString(1, course.getCourseStatus().getCourseStatus());
//            statement.setInt(2, course.getId());
//            statement.execute();
//        } catch (SQLException e) {
//            LOG.error(String.format("Error updating course with status: %s", course.getCourseStatus()), e);
//        }
//    }

    private List<Course> getCourseList(ResultSet rs) throws SQLException {
        List<Course> courses = new ArrayList<>();
        while (rs.next()) {
            courses.add(mapCourse(rs));
        }
        return courses;
    }

    private Course getCourse(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return mapCourse(rs);
        }
        return null;
    }

    private Course mapCourse(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setId(rs.getInt("id"));
        course.setTitle(rs.getString("title"));
        course.setCourseStatus(CourseStatus.getCourseStatusValue(rs.getString("status")).get());
        return course;
    }
}
