package com.courses.management.solution.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.solution.SolutionDAOImpl;
import com.courses.management.solution.SolutionDao;
import com.courses.management.solution.Solutions;

public class UpdateSolution implements Command {
    private final View view;
    private SolutionDao solutionsDAO;

    public UpdateSolution(View view) {
        this.view = view;
        solutionsDAO = new SolutionDAOImpl();
    }

    @Override
    public String command() {
        return "update_solution";
    }

    @Override
    public void process() {
        view.write("Enter solution ID for update");
        int id = validateNumber(view.read());
        view.write("Enter update to solution text");
        String text = validate(view.read());
        view.write("Enter updated to solution mark");
        int mark = validateNumber(view.read());
        Solutions solution = new Solutions();
        solution.setId(id);
        solution.setText(text);
        solution.setMark(mark);
        solutionsDAO.update(solution);
        view.write(String.format("Solution updated: %s", solution.getText()));
    }

    private String validate(String value) {
        while (value.trim().isEmpty()){
            view.write("Please enter the correct title!");
            value = view.read();
        }
        return value;
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
