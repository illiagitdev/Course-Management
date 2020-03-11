package com.courses.management.solution;

import com.courses.management.common.DataAccessObject;

import java.util.List;

public interface SolutionDao extends DataAccessObject<Solutions> {
    List<Solutions> getAllByUser(int id);
    List<Solutions> getAllByHomework(int id);
    Solutions get(int userId, int homeworkId);
}
