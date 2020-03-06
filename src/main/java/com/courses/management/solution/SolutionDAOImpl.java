package com.courses.management.solution;

import com.courses.management.common.DataAccessObject;

import java.util.List;

public class SolutionDAOImpl implements DataAccessObject<Solutions>, SolutionDao {
    @Override
    public void create(Solutions solutions) {
        
    }

    @Override
    public void update(Solutions solutions) {

    }

    @Override
    public void delete(int id) {

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
