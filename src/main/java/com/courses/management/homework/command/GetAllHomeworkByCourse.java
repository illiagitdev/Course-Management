package com.courses.management.homework.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.homework.Homework;
import com.courses.management.homework.HomeworkDAO;
import com.courses.management.homework.HomeworkDAOImpl;

import java.util.List;

public class GetAllHomeworkByCourse implements Command {
    private final View view;
    private HomeworkDAO homeworkDAO;

    public GetAllHomeworkByCourse(View view) {
        this.view = view;
        homeworkDAO = new HomeworkDAOImpl();
    }

    @Override
    public String command() {
        return "homework_by_course_id";
    }

    @Override
    public void process() {
        view.write("Enter a course id");
        int id = validateNumber(view.read());
        List<Homework> homework = homeworkDAO.getAll(id);
        view.write("Homework");
        for (Homework h: homework) {
            view.write(String.format("%d\t%s\t%s\t%s", h.getId(), h.getTitle(), h.getText(), h.getPath()));
        }
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
