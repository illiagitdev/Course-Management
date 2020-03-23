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
            "status = ?, course_id = ? WHERE email = ?;";
    private static final String DELETE = "DELETE FROM users WHERE id = ?;";
    private static final String FIND_USER_BY_ID = "SELECT id, first_name, last_name, email, user_role, status " +
            "FROM users WHERE id = ?;";
    private static final String FIND_USER_BY_EMAIL = "SELECT id, first_name, last_name, email, user_role, status " +
            "FROM users WHERE email = ?;";
    private static final String FIND_ALL_USERS = "SELECT id, first_name, last_name, email, user_role, status FROM users;";
    private static final String DELETE_BY_EMAIL = "DELETE FROM users WHERE email = ?;";
    private static final String GET_BY_USER_STATUS = "SELECT id, first_name, last_name, email, user_role, status, course_id " +
            "FROM users WHERE id = ?;";
    private static final String GET_ALL_BY_COURSE_STATUS = "SELECT id, first_name, last_name, email, user_role, status, course_id " +
            "FROM users WHERE status = ? AND course_id = (SELECT id FROM  course WHERE title LIKE ?);";
    private static final String GET_BY_FIRST_LAST_NAMES = "SELECT id, first_name, last_name, email, user_role, status, course_id " +
            "FROM users WHERE first_name = ? AND last_name = ?;";
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
            statement.setInt(6, user.getCourse().getId());
            statement.setString(7, user.getEmail());
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
    public void deleteByEmail(String email) {
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
    public List<User> getByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public List<User> getByStatus(String status) {
        return null;
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

    @Override
    public List<User> getByCourseAndStatus(String courseTitle, String status) {
        return null;
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
