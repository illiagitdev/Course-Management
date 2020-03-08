package com.courses.management.homework.command;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.homework.Homework;
import com.courses.management.homework.HomeworkDAOImpl;

public class CreateHomework implements Command {
    private final View view;
    private DataAccessObject<Homework> homeworkDAO;

    public CreateHomework(View view) {
        this.view = view;
        homeworkDAO = new HomeworkDAOImpl();
    }

    @Override
    public String command() {
        return "create_homework";
    }

    @Override
    public void process() {
        view.write("Enter a homework title");
        String title = validate(view.read());
        view.write("Enter a homework text");
        String text = validate(view.read());
        view.write("Enter a path to homework");
        String path = validate(view.read());
        Homework homework = new Homework();
        homework.setTitle(title);
        homework.setText(text);
        homework.setPath(path);
        homeworkDAO.create(homework);
        view.write(String.format("Homework created with title: %s", homework.getTitle()));
    }

    private String validate(String value) {
        while (value.trim().isEmpty()){
            view.write("Please enter the correct title!");
            value = view.read();
        }
        return value;
    }
}
