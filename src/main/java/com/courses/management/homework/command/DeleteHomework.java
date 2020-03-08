package com.courses.management.homework.command;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.homework.Homework;
import com.courses.management.homework.HomeworkDAOImpl;

public class DeleteHomework implements Command {
    private final View view;
    private DataAccessObject<Homework> homeworkDAO;

    public DeleteHomework(View view) {
        this.view = view;
        homeworkDAO = new HomeworkDAOImpl();
    }

    @Override
    public String command() {
        return "delete_homework";
    }

    @Override
    public void process() {
        view.write("Enter homework ID to delete");
        int id = validateNumber(view.read());
        homeworkDAO.delete(id);
        view.write(String.format("homework with id = %s deleted", id));
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
