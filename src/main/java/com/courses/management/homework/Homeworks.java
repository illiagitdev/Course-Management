package com.courses.management.homework;

import org.apache.commons.fileupload.FileItem;

import java.io.File;

public class Homeworks {
    private final HomeworkDAO homeworkDAO;

    public Homeworks(HomeworkDAOImpl homeworkDAO) {
        this.homeworkDAO = homeworkDAO;
    }

    public void uploadFile(FileItem item, String title, Integer courseId) {
        try {
            if (!item.isFormField()) {
                final String name = new File(item.getName()).getName();
                item.write(new File("d:\\java\\projects\\files\\courseManagement\\" + File.separator + name));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error when loading file", e);
        }
    }

    public Homework getHomework(int id) {
        return homeworkDAO.get(id);
    }
}
