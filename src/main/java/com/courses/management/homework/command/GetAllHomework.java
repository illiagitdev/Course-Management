package com.courses.management.homework.command;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.homework.Homework;
import com.courses.management.homework.HomeworkDAOImpl;

import java.util.List;

public class GetAllHomework implements Command {
    private final View view;
    private DataAccessObject<Homework> homeworkDAO;

    public GetAllHomework(View view) {
        this.view = view;
        homeworkDAO = new HomeworkDAOImpl();
    }

    @Override
    public String command() {
        return "get_all_homework";
    }

    @Override
    public void process() {
        List<Homework> homework = homeworkDAO.getAll();
        view.write("Homework");
        for (Homework h: homework) {
            view.write(String.format("%d\t%s\t%s\t%s", h.getId(), h.getTitle(), h.getText(), h.getPath()));
        }
    }
}
