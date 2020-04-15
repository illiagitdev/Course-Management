package com.courses.management.homework;

import com.courses.management.config.HibernateDatabaseConnector;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.SessionFactory;

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
public class HomeworkServlet extends HttpServlet {
    private Homeworks service;

    @Override
    public void init() throws ServletException {
        super.init();
        final SessionFactory sessionFactory = HibernateDatabaseConnector.getSessionFactory();
        service = new Homeworks(new HomeworkDAOImpl(sessionFactory));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = getAction(req);

        if (action.startsWith("/upload")) {
            req.getRequestDispatcher("/view/create-homework.jsp").forward(req, resp);
        } else if (action.startsWith("/get")) {
            String idValue = req.getParameter("id");
            int id = Integer.parseInt(idValue);
            Homework homework = service.getHomework(id);
//            File file = new File(homework.getPath());
            File file = new File("d:\\java\\projects\\files\\courseManagement\\1768019004_Партер_R-27_P-59_1768019004_637179799764400152.pdf");
            file.exists();
            resp.setHeader("Content-Type", getServletContext().getMimeType(file.getName()));
            resp.setHeader("Content-Length", String.valueOf(file.length()));
            resp.setHeader("Content-Disposition", "inline; filename=\"1768019004_Партер_R-27_P-59_1768019004_637179799764400152.pdf\"");
            Files.copy(file.toPath(), resp.getOutputStream());
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = getAction(req);

        if (action.startsWith("/upload")) {
            if (ServletFileUpload.isMultipartContent(req)) {
                try {
                    List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);

                    req.setAttribute("message", "File Uploaded Successfully");
                } catch (Exception e) {
                    req.setAttribute("message", "File upload fail due to " + e);
                }
            } else {
                req.setAttribute("message", "File not found");
            }
        }
        req.getRequestDispatcher("/view/homework_uploaded.jsp").forward(req, resp);
    }

    private String getAction (HttpServletRequest request) {
        final String requestURI = request.getRequestURI();
        final String requestPathWithServletContext = request.getContextPath() + request.getServletPath();
        return requestURI.substring(requestPathWithServletContext.length());
    }
}
