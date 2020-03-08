package com.courses.management.homework.command;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.homework.Homework;
import com.courses.management.homework.HomeworkDAOImpl;

public class UpdateHomework implements Command {
    private final View view;
    private DataAccessObject<Homework> homeworkDAO;

    public UpdateHomework(View view) {
        this.view = view;
        homeworkDAO = new HomeworkDAOImpl();
    }

    @Override
    public String command() {
        return "update_homework";
    }

    @Override
    public void process() {
        view.write("Enter homework ID for update");
        int id = validateNumber(view.read());
        view.write("Enter update to homework title");
        String title = validate(view.read());
        view.write("Enter update to homework text");
        String text = validate(view.read());
        view.write("Enter updated path to homework");
        String path = validate(view.read());
        Homework homework = new Homework();
        homework.setId(id);
        homework.setTitle(title);
        homework.setText(text);
        homework.setPath(path);
        homeworkDAO.update(homework);
        view.write(String.format("Homework updated with title: %s", homework.getTitle()));
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
