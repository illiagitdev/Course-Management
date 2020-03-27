package com.courses.management.course;

import com.courses.management.common.DatabaseConnector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/course/*")
public class CourseServlet extends HttpServlet {
    private Courses servise;

    @Override
    public void init() throws ServletException {
        super.init();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        servise = new Courses(databaseConnector.getDataSource());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        if (action.startsWith("/showCourses")) {
            List<Course> courses = servise.showCourses();
            req.setAttribute("courses", courses);
            req.getRequestDispatcher("/view/show_courses.jsp").forward(req, resp);
        }

        if (action.startsWith("/get")) {
            final String id = req.getParameter("id");
            final Course course = servise.getById(Integer.valueOf(id));
        }

        super.doGet(req, resp);
    }

    private String getAction (HttpServletRequest request) {
        final String requestURI = request.getRequestURI();
        final String requestPathWithServletContext = request.getContextPath() + request.getServletPath();
        return requestURI.substring(requestPathWithServletContext.length());
    }
}
