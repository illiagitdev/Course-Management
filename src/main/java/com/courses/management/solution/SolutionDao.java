package com.courses.management.solution;

import java.util.List;

public interface SolutionDao {
    List<Solutions> getAllByUser(int id);
    List<Solutions> getAllByHomework(int id);
    Solutions get(int userId, int homeworkId);
}
