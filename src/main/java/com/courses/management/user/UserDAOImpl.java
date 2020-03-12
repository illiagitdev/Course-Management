package com.courses.management.user;

import com.courses.management.common.DatabaseConnector;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAOImpl;
import com.courses.management.solution.SolutionDAOImpl;
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

public class UserDAOImpl implements UserDAO {
    private static final Logger LOG = LogManager.getLogger(SolutionDAOImpl.class);
    private HikariDataSource dataSource = DatabaseConnector.getConnector();
    private static final String INSERT = "INSERT INTO users(first_name, last_name, email, user_role, status) " +
            "VALUES(?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE users SET first_name = ?, last_name = ?, email = ? " +
            "WHERE id = ?;";
    private static final String DELETE = "DELETE FROM users WHERE id = ?;";
    private static final String GET_BY_ID = "SELECT id, first_name, last_name, email, user_role, status, course_id " +
            "FROM users WHERE id = ?;";
    private static final String GET_BY_EMAIL = "SELECT id, first_name, last_name, email, user_role, status, course_id " +
            "FROM users WHERE email = ?;";
    private static final String GET_ALL = "SELECT id, first_name, last_name, email, user_role, status, course_id FROM users;";
    private static final String GET_ALL_BY_COURSE_STATUS = "SELECT id, first_name, last_name, email, user_role, status, course_id " +
            "FROM users WHERE status = ? AND course_id = (SELECT id FROM  course WHERE title LIKE ?);";

    @Override
    public void create(User user) {
        LOG.debug(String.format("create: user.firstName=%s, .lastName=%s", user.getFirstName(), user.getLastName()));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)){
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUserRole().getRole());
            statement.setString(5, user.getStatus().getStatus());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("Error creating user: %s", user.getFirstName()), e);
        }
    }

    @Override
    public void update(User user) {
        LOG.debug(String.format("update: user.id=%d", user.getId()));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)){
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getId());
            int rowAffected = statement.executeUpdate();
            if (rowAffected == 0 || rowAffected > 1) {
                LOG.warn(String.format("update: affected rows=%d", rowAffected));
            }
        } catch (SQLException e) {
            LOG.error(String.format("Error creating user: %s", user.getFirstName()), e);
        }

    }

    @Override
    public void delete(int id) {
        LOG.debug(String.format("delete: users.id=%s", id));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setInt(1, id);
            int index = statement.executeUpdate();
            if (index == 0 ){
                LOG.warn(String.format("no user with id=%s found", id));
            }
        } catch (SQLException e) {
            LOG.error(String.format("Error deleting user with id: %s", id), e);
        }
    }

    @Override
    public User get(int id) {
        User user = null;
        LOG.debug(String.format("get(id): user.id=%d", id));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            user = buildUser(resultSet);
        } catch (SQLException e) {
            LOG.error("Error retrieving user.", e);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        User user = null;
        LOG.debug(String.format("get(id): user.email=%s", email));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_EMAIL)){
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            user = buildUser(resultSet);
        } catch (SQLException e) {
            LOG.error("Error retrieving user.", e);
        }
        return user;
    }

    @Override
    public List<User> getByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public List<User> getByStatus(String status) {
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = statement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
                String role = resultSet.getString(5);
                user.setUserRole(UserRole.valueOf(role));
                String status = resultSet.getString(6);
                user.setStatus(UserStatus.valueOf(status));
                users.add(user);
            }
        } catch (SQLException e) {
            LOG.error("Error retrieving users.", e);
        }
        return users;
    }

    @Override
    public List<User> getByCourseAndStatus(String courseTitle, String status) {
        List<User> users = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_COURSE_STATUS)){
            statement.setString(1 , status);
            statement.setString(2 , courseTitle);
            ResultSet resultSet = statement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()){
                User user = buildUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            LOG.error("Error retrieving users by course id.", e);
        }
        return users;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        Optional<UserRole> someRole = UserRole.getUserRole(resultSet.getString("user_role").toUpperCase());
        UserRole role = someRole.isEmpty() ? null : someRole.get();
        user.setUserRole(role);
        Optional<UserStatus> status = UserStatus.getUserStatus(resultSet.getString("status").toUpperCase());
        UserStatus userStatus = status.isEmpty() ? null : status.get();
        user.setStatus(userStatus);
        int courseId = resultSet.getInt("course_id");
        /*  call CourseDAOImpl to get course for user*/
        Course course =  null;
        if(courseId > 0) {
            course = (new CourseDAOImpl()).get(courseId);
        }
        user.setCourse(course);
        return user;
    }
}
