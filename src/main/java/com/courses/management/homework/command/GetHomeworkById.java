package com.courses.management.homework.command;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.homework.Homework;
import com.courses.management.homework.HomeworkDAOImpl;

public class GetHomeworkById implements Command {
    private final View view;
    private DataAccessObject<Homework> homeworkDAO;

    public GetHomeworkById(View view) {
        this.view = view;
        homeworkDAO = new HomeworkDAOImpl();
    }

    @Override
    public String command() {
        return "homework_by_id";
    }

    @Override
    public void process() {
        view.write("Enter a homework id");
        int id = validateNumber(view.read());
        Homework homework = homeworkDAO.get(id);
        view.write("Homework:");
        view.write(String.format("%d\t%s\t%s\t%s", homework.getId(), homework.getTitle(), homework.getText(),
                homework.getPath()));
        view.write(String.format("Course with id = %s retrieved", id));
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
