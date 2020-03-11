package com.courses.management.solution.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.solution.SolutionDAOImpl;
import com.courses.management.solution.SolutionDao;
import com.courses.management.solution.Solutions;

import java.util.List;

public class GetAllSolutions implements Command {
    private final View view;
    private SolutionDao solutionsDAO;

    public GetAllSolutions(View view) {
        this.view = view;
        solutionsDAO = new SolutionDAOImpl();
    }

    @Override
    public String command() {
        return "get_all_solutions";
    }

    @Override
    public void process() {
        List<Solutions> solutions = solutionsDAO.getAll();
        view.write("Solutions");
        for (Solutions sol: solutions) {
            view.write(String.format("id=%d, text='%s', status=%s, mark=%d",
                    sol.getId(), sol.getText(), sol.getStatus(), sol.getMark()));
        }
    }
}
