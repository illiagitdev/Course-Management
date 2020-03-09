package com.courses.management.solution.command;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.solution.SolutionDAOImpl;
import com.courses.management.solution.Solutions;

public class GetSolutionById implements Command {
    private final View view;
    private DataAccessObject<Solutions> solutionsDAO;

    public GetSolutionById(View view) {
        this.view = view;
        solutionsDAO = new SolutionDAOImpl();
    }

    @Override
    public String command() {
        return "solution_by_id";
    }

    @Override
    public void process() {
        view.write("Enter solution ID");
        int id = validateNumber(view.read());
        Solutions sol = new Solutions();
        solutionsDAO.get(id);
        view.write(String.format("id=%d, text='%s', status=%s, mark=%d",
                sol.getId(), sol.getText(), sol.getStatus(), sol.getMark()));
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
