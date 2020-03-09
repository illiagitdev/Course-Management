package com.courses.management.user;

import com.courses.management.common.DataAccessObject;
import com.courses.management.common.DatabaseConnector;
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

public class UserDAOImpl implements DataAccessObject<User>, UserDAO {
    private static final Logger LOG = LogManager.getLogger(SolutionDAOImpl.class);
    private HikariDataSource dataSource = DatabaseConnector.getConnector();
    private static final String INSERT = "INSERT INTO users(first_name, last_name, email, user_role, status) " +
            "VALUES(?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE users SET first_name = ?, last_name = ?, email = ? " +
            "WHERE id = ?;";
    private static final String DELETE = "DELETE FROM users WHERE id = ?;";
    private static final String GET_BY_ID = "SELECT id, first_name, last_name, email, user_role, status " +
            "FROM users WHERE id = ?;";
    private static final String GET_ALL = "SELECT id, first_name, last_name, email, user_role, status FROM users;";
    private static final String GET_ALL_BY_COURSE_ID = "SELECT id, first_name, last_name, email, user_role, status " +
            "FROM users WHERE course_id = ?;";

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
        LOG.debug(String.format("get(id): user.id=%s", id));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            user = new User();
            user.setId(resultSet.getInt(1));
            user.setFirstName(resultSet.getString(2));
            user.setLastName(resultSet.getString(3));
            user.setEmail(resultSet.getString(4));
            String role = resultSet.getString(5);
            user.setUserRole(UserRole.valueOf(role));
            String status = resultSet.getString(6);
            user.setStatus(UserStatus.valueOf(status));
        } catch (SQLException e) {
            LOG.error("Error retrieving user.", e);
        }
        return user;
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
    public List<User> getByCourse(int courseId) {
        List<User> users = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_COURSE_ID)){
            statement.setInt(1 ,courseId);
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
            LOG.error("Error retrieving users by course id.", e);
        }
        return users;
    }
}
