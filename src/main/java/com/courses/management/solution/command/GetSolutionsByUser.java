package com.courses.management.solution.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.solution.SolutionDAOImpl;
import com.courses.management.solution.SolutionDao;
import com.courses.management.solution.Solutions;

import java.util.List;

public class GetSolutionsByUser implements Command {
    private final View view;
    private SolutionDao solutionsDAO;

    public GetSolutionsByUser(View view) {
        this.view = view;
        solutionsDAO = new SolutionDAOImpl();
    }

    @Override
    public String command() {
        return "all_solutions_by_user";
    }

    @Override
    public void process() {
        view.write("Enter user ID");
        int id = validateNumber(view.read());
        List<Solutions> solutions = solutionsDAO.getAllByUser(id);
        view.write("Solutions");
        for (Solutions sol: solutions) {
            view.write(String.format("id=%d, text='%s', status=%s, mark=%d",
                    sol.getId(), sol.getText(), sol.getStatus(), sol.getMark()));
        }
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
