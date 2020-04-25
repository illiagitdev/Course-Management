package com.courses.management.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/homework/*")
public class HomeworkController {
    private Homeworks homeworks;

    @Autowired
    public void setHomeworks(Homeworks homeworks) {
        this.homeworks = homeworks;
    }

    @RequestMapping(path = "/upload", method = RequestMethod.GET)
    public String getUploadFileView(@RequestParam(name = "course_id") String course_id, Model model) {
        model.addAttribute("course_id", course_id);
        return "create-homework";
    }
}
