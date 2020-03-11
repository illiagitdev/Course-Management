package com.courses.management.solution.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.solution.SolutionDAOImpl;
import com.courses.management.solution.SolutionDao;

public class DeleteSolution implements Command {
    private final View view;
    private SolutionDao solutionsDAO;

    public DeleteSolution(View view) {
        this.view = view;
        solutionsDAO = new SolutionDAOImpl();
    }

    @Override
    public String command() {
        return "delete_solution";
    }

    @Override
    public void process() {
        view.write("Enter solution ID for delete");
        int id = validateNumber(view.read());
        solutionsDAO.delete(id);
        view.write(String.format("Solution with id = %s deleted", id));
    }

    private int validateNumber(String value) {
        int res = 0;
        try {
            res = Integer.parseInt(value);
        } catch (RuntimeException e) {
            view.write("Input not a number!");
        }
        return res;
    }
}
