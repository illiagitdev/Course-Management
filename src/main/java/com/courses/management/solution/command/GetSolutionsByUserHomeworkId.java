package com.courses.management.solution.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.solution.SolutionDAOImpl;
import com.courses.management.solution.SolutionDao;
import com.courses.management.solution.Solutions;

public class GetSolutionsByUserHomeworkId implements Command {
    private final View view;
    private SolutionDao solutionsDAO;

    public GetSolutionsByUserHomeworkId(View view) {
        this.view = view;
        solutionsDAO = new SolutionDAOImpl();
    }

    @Override
    public String command() {
        return "solutions_by_user_homework_id";
    }

    @Override
    public void process() {
        view.write("Enter user ID");
        int userId = validateNumber(view.read());
        view.write("Enter homework ID");
        int homeworkId = validateNumber(view.read());
        Solutions sol = solutionsDAO.get(userId, homeworkId);
        view.write("Solutions");
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
