package com.courses.management.solution.command;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.solution.SolutionDAOImpl;
import com.courses.management.solution.SolutionStatus;
import com.courses.management.solution.Solutions;

public class CreateSolution implements Command {
    private final View view;
    private DataAccessObject<Solutions> solutionsDAO;

    public CreateSolution(View view) {
        this.view = view;
        solutionsDAO = new SolutionDAOImpl();
    }

    @Override
    public String command() {
        return "create_solution";
    }

    @Override
    public void process() {
        view.write("Enter solution text:");
        String text = validate(view.read());
        Solutions solutions = new Solutions();
        solutions.setText(text);
        solutions.setStatus(SolutionStatus.NEW);
        solutions.setMark(0);
        solutionsDAO.create(solutions);
        view.write(String.format("Solution created, status: %s", solutions.getStatus()));
    }

    private String validate(String value) {
        while (value.trim().isEmpty()){
            view.write("Please enter the correct title!");
            value = view.read();
        }
        return value;
    }
}
