package com.courses.management.course;

import com.courses.management.common.Validator;
import com.courses.management.common.exceptions.ErrorMessage;
import com.courses.management.config.HibernateDatabaseConnector;
import com.courses.management.user.UserDAOImpl;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@WebServlet(urlPatterns = "/course/*")
public class CourseServlet extends HttpServlet {
    private Courses servise;

    @Override
    public void init() throws ServletException {
        super.init();
        final SessionFactory sessionFactory = HibernateDatabaseConnector.getSessionFactory();
        servise = new Courses(new CourseDAOImpl(sessionFactory), new UserDAOImpl(sessionFactory));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        if (action.startsWith("/showCourses")) {
            List<Course> courses = servise.showCourses();
            req.setAttribute("courses", courses);
            req.getRequestDispatcher("/view/show_courses.jsp").forward(req, resp);
        } else if (action.startsWith("/get")) {
            final String id = req.getParameter("id");
            final Course course = servise.getById(Integer.valueOf(id));
            req.setAttribute("course", course);
            req.getRequestDispatcher("/view/course_details.jsp").forward(req, resp);
        } else if (action.startsWith("/createCourse")) {
            req.setAttribute("courseStatuses", CourseStatus.values());
            req.getRequestDispatcher("/view/create_course.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);
        if (action.startsWith("/createCourse")) {
            Course course = mapCourse(req);

            List<ErrorMessage> errorMessage = validateCourse(course);
            if (!errorMessage.isEmpty()) {
                req.setAttribute("errors", errorMessage);
                req.setAttribute("courseStatuses", CourseStatus.values());
                req.getRequestDispatcher("/view/create_course.jsp").forward(req, resp);
            } else {
                servise.createCourse(course);
                req.setAttribute("course_title", course.getTitle());
                req.getRequestDispatcher("/view/course_created.jsp").forward(req, resp);
            }
        }
    }

    private List<ErrorMessage> validateCourse(Course course) {
        final List<ErrorMessage> errorMessages = Validator.validateEntity(course);
        Course persistentCourse = servise.getByTitle(course.getTitle());
        if (Objects.nonNull(persistentCourse) && !persistentCourse.getTitle().isEmpty()) {
            errorMessages.add(new ErrorMessage("", "course with title already exists"));
        }
        return errorMessages;
    }

    private Course mapCourse(HttpServletRequest req) {
        final String courseTitle = req.getParameter("title").trim();
        final String course_status = req.getParameter("course_status").trim();
        final Optional<CourseStatus> courseStatus = CourseStatus.getCourseStatusValue(course_status);
        Course course = new Course();
        course.setTitle(courseTitle);
        course.setCourseStatus(courseStatus.get());
        return course;
    }

    private String getAction (HttpServletRequest request) {
        final String requestURI = request.getRequestURI();
        final String requestPathWithServletContext = request.getContextPath() + request.getServletPath();
        return requestURI.substring(requestPathWithServletContext.length());
    }
}
