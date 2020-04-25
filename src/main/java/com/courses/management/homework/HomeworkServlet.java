package com.courses.management.homework;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@WebServlet(urlPatterns = "/homework/*")
@Configurable
public class HomeworkServlet extends HttpServlet {
    private Homeworks homeworks;

    public HomeworkServlet() {
    }

    @Autowired
    public void setHomeworks(Homeworks homeworks){
        this.homeworks = homeworks;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = getAction(req);

        if (action.startsWith("/upload")) {
            String courseId = req.getParameter("course_id");
            req.setAttribute("courseId", courseId);
            req.getRequestDispatcher("/view/create-homework.jsp").forward(req, resp);
        } else if (action.startsWith("/get")) {
            getHomework(req, resp);
        } else if (action.startsWith("/preview")) {
            final String homeworkId = req.getParameter("id");
            req.setAttribute("homeworkId", homeworkId);
            req.getRequestDispatcher("/view/preview-homework.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = getAction(req);

        if (action.startsWith("/upload")) {
            Integer courseId = null;
            if (ServletFileUpload.isMultipartContent(req)) {
                try {
                    courseId = Integer.valueOf(req.getParameter("course_id"));
                    List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);

                    homeworks.uploadFile(multiparts, courseId);
                } catch (Exception e) {
                    processError(req, resp, "File upload fail due to " + e.getMessage(),
                            "/view/create_homework.jsp");
                }
            } else {
                processError(req, resp, "File not found", "/view/create_homework.jsp");
            }
            resp.sendRedirect(String.format("/courseManagement_1_0_war/course/get?id=%s", courseId));
        }
    }

    private void getHomework(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Integer homeworkId = Integer.valueOf(req.getParameter("id"));
        Homework homework = homeworks.getHomework(homeworkId);
        File file = new File(homework.getPath());
        if (!file.exists()) {
            processError(req, resp, "No File Found", "/view/course-details.jsp");
        }
        resp.setHeader("Content-Type", getServletContext().getMimeType(file.getName()));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        resp.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", homework.getTitle()));
        Files.copy(file.toPath(), resp.getOutputStream());
    }

    private void processError(HttpServletRequest req, HttpServletResponse resp, String message, String jspPath)
            throws ServletException, IOException {
        req.setAttribute("error", message);
        req.getRequestDispatcher(jspPath).forward(req, resp);
    }

    private String getAction (HttpServletRequest request) {
        final String requestURI = request.getRequestURI();
        final String requestPathWithServletContext = request.getContextPath() + request.getServletPath();
        return requestURI.substring(requestPathWithServletContext.length());
    }
}
