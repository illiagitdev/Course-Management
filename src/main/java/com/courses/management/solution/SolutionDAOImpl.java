package com.courses.management.solution;

import com.courses.management.common.DataAccessObject;
import com.courses.management.common.DatabaseConnector;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SolutionDAOImpl implements DataAccessObject<Solutions>, SolutionDao {
    private static final Logger LOG = LogManager.getLogger(SolutionDAOImpl.class);
    private HikariDataSource dataSource = DatabaseConnector.getConnector();
    private static final String INSERT = "INSERT INTO solution(text, status, mark) VALUES(?, ?, ?);";
    private static final String UPDATE = "UPDATE solution SET text = ?, mark = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM solution WHERE id = ?;";

    @Override
    public void create(Solutions solutions) {
        LOG.debug(String.format("create: solutions.text=%s", solutions.getText()));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)){
            statement.setString(1, solutions.getText());
            statement.setString(2, solutions.getStatus().getStatus());
            statement.setInt(3, solutions.getMark());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("Error creating solution: %s", solutions.getText()), e);
        }
    }

    @Override
    public void update(Solutions solutions) {
        LOG.debug(String.format("update: solutions.text=%s", solutions.getText()));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)){
            statement.setString(1, solutions.getText());
            statement.setInt(2, solutions.getMark());
            statement.setInt(3, solutions.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("Error creating solution: %s", solutions.getText()), e);
        }
    }

    @Override
    public void delete(int id) {
        LOG.debug(String.format("delete: solution.id=%s", id));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)){
            statement.setInt(1, id);
            int index = statement.executeUpdate();
            if (index == 0 ){
                LOG.warn(String.format("no solution with id=%s found", id));
            }
        } catch (SQLException e) {
            LOG.error(String.format("Error deleting solution with id: %s", id), e);
        }
    }

    @Override
    public Solutions get(int id) {
        return null;
    }

    @Override
    public List<Solutions> getAll() {
        return null;
    }

    @Override
    public List<Solutions> getAllByUser(int id) {
        return null;
    }

    @Override
    public List<Solutions> getAllByHomework(int id) {
        return null;
    }

    @Override
    public Solutions get(int userId, int homeworkId) {
        return null;
    }
}
