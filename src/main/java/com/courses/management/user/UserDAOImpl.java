package com.courses.management.user;

import com.courses.management.common.exceptions.SQLUserException;
import com.courses.management.solution.SolutionDAOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOG = LogManager.getLogger(SolutionDAOImpl.class);
    private static final String INSERT = "INSERT INTO users(first_name, last_name, email, user_role, status) " +
            "VALUES(?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE users SET first_name = ?, last_name = ?, email = ?, user_role = ?, " +
            "status = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM users WHERE id = ?;";
    private static final String FIND_USER_BY_ID = "SELECT id, first_name, last_name, email, user_role, status " +
            "FROM users WHERE id = ?;";
    private static final String FIND_USER_BY_EMAIL = "SELECT id, first_name, last_name, email, user_role, status " +
            "FROM users WHERE email = ?;";
    private static final String FIND_ALL_USERS = "SELECT id, first_name, last_name, email, user_role, status FROM users;";
    private static final String UPDATE_REMOVE_COURSE_AND_SET_STATUS = "UPDATE users SET course_id = NULL, status = ? " +
            "WHERE email = ?;";
    private static final String FIND_USERS_BY_COURSE_TITLE = "SELECT u.id, u.first_name, u.last_name, u.email, u.user_role," +
            "u.status FROM users u " +
            "INNER JOIN course c ON u.course_id = c.id " +
            "WHERE c.title = ?;";
    private static final String FIND_ALL_USERS_BY_STATUS = "SELECT id, first_name, last_name, email, user_role, status " +
            "FROM users WHERE status = ?;";
    private DataSource dataSource;

    public UserDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(User user) {
        LOG.debug(String.format("create: user.firstName=%s, lastName=%s, email=%s", user.getFirstName(),
                user.getLastName(), user.getEmail()));
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
            throw new SQLUserException("Error occurred when creating user");
        }
    }

    @Override
    public void update(User user) {
        LOG.debug(String.format("update: user.firstName=%s, lastName=%s, email=%s", user.getFirstName(),
                user.getLastName(), user.getEmail()));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)){
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUserRole().getRole());
            statement.setString(5, user.getStatus().getStatus());
            statement.setInt(6, user.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("Error creating user: %s", user.getFirstName()), e);
            throw new SQLUserException("Error occurred when updating user");
        }
    }

    @Override
    public void delete(int id) {
        LOG.debug(String.format("delete: user_id=%s", id));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("Error deleting user with id: %s", id), e);
            throw new SQLUserException("Error occurred when deleting user");
        }
    }

    @Override
    public User get(int id) {
        LOG.debug(String.format("get: user.id=%d", id));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)){
            statement.setInt(1, id);
            return getUser(statement.executeQuery());
        } catch (SQLException e) {
            LOG.error(String.format("get: user.id=%d", id), e);
            throw new SQLUserException("Error occurred when retrieving user");
        }
    }

    @Override
    public User get(String email) {
        LOG.debug(String.format("get: user.email =%s", email));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL)){
            statement.setString(1, email);
            return getUser(statement.executeQuery());
        } catch (SQLException e) {
            LOG.error(String.format("get: user.email =%s", email), e);
            throw new SQLUserException("Error occurred when retrieving user");
        }
    }

    @Override
    public void removeUserCourseAndSetStatus(String email, UserStatus status) {
        LOG.debug(String.format("removeUserCourseAndSetStatus: user.email =%s", email));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_REMOVE_COURSE_AND_SET_STATUS)){
            statement.setString(1, status.name());
            statement.setString(2, email);
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("removeUserCourseAndSetStatus: user.email =%s", email), e);
            throw new SQLUserException("Error occurred when removing course from a user");
        }
    }

    @Override
    public List<User> getUsersByCourse(String courseTitle) {
        LOG.debug(String.format("getUsersByCourse: course.title=%s", courseTitle));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USERS_BY_COURSE_TITLE)){
            statement.setString(1, courseTitle);
            return getUsersList(statement.executeQuery());
        } catch (SQLException e) {
            LOG.error(String.format("getUsersByCourse: course.title=%s", courseTitle), e);
            throw new SQLUserException("Error occurred when retrieving users by course title");
        }
    }

    @Override
    public List<User> getAllByStatus(UserStatus status) {
        LOG.debug(String.format("getAllByStatus: course.status=%s", status.name()));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS_BY_STATUS)){
            statement.setString(1, status.name());
            return getUsersList(statement.executeQuery());
        } catch (SQLException e) {
            LOG.error(String.format("getAllByStatus: course.status=%s", status.name()), e);
            throw new SQLUserException("Error occurred when retrieving users by status");
        }
    }

    @Override
    public List<User> getAll() {
        LOG.debug("getAll: ");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)){
            return getUsersList(statement.executeQuery());
        } catch (SQLException e) {
            LOG.error("getAll:", e);
            throw new SQLUserException("Error occurred when retrieving list of users");
        }
    }

    private List<User> getUsersList(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(mapUserFromRS(rs));
        }
        return users;
    }

    private User getUser(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return mapUserFromRS(rs);
        }
        return null;
    }

    private User mapUserFromRS(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setUserRole(UserRole.getUserRole(rs.getString("user_role")).get());
        user.setStatus(UserStatus.getUserStatus(rs.getString("status")).get());
        return user;
    }
}
