package com.courses.management.homework;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping(value = "/homework")
public class HomeworkController {
    private static final Logger LOG = LogManager.getLogger(HomeworkController.class);
    private HomeworkService homeworks;

    @Autowired
    public void setHomeworks(HomeworkService homeworks) {
        this.homeworks = homeworks;
    }

    @GetMapping(path = "/upload")
    public String getUploadFileView(@RequestParam(name = "course_id") String courseId, Model model) {
        model.addAttribute("course_id", courseId);
        return "create-homework";
    }

    @PostMapping(path = "/upload")
    public ModelAndView uploadFile(@RequestParam("course_id") Integer courseId, HttpServletRequest request,
                                   ModelMap model) {
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                homeworks.uploadFile(multiparts, courseId);
            } catch (Exception e) {
                LOG.error(String.format("uploadFile: courseId = %s", courseId));
                model.addAttribute("error", "File upload failed due to" + e);
                return new ModelAndView("create-homework", model);
            }
        } else {
            model.addAttribute("error", "No file found");
            return new ModelAndView("create-homework", model);
        }
        return new ModelAndView(String.format("redirect:/course/get?id=%s", courseId));
    }

    @GetMapping(path = "/preview")
    public String previewHomework(@RequestParam(name = "id") String id, Model model) {
        model.addAttribute("homeworkId", id);
        return "preview-homework";
    }


    @GetMapping(path = "/getFile")
    public void getHomeworkFile(@RequestParam(name = "homeworkId") Integer homeworkId, HttpServletResponse response)
            throws IOException {
        Homework homework = homeworks.getHomework(homeworkId);
        File file = new File(homework.getPath());
        if (!file.exists()) {
            throw new FileNotFoundException("No file found");
        }
        response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(file.getName()));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", homework.getTitle()));
        Files.copy(file.toPath(), response.getOutputStream());
    }

    @ExceptionHandler({FileNotFoundException.class})
    public ModelAndView handleException(FileNotFoundException ex) {
        LOG.error("handleException: ", ex);
        ModelAndView model = new ModelAndView("file-not-found");
        model.addObject("error", ex.getMessage());
        model.setStatus(HttpStatus.BAD_REQUEST);
        return model;
    }
}
