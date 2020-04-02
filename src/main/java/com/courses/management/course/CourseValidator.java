package com.courses.management.course;

import com.courses.management.common.exceptions.ErrorMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CourseValidator {

    public static ErrorMessage validateCreateCourse(HttpServletRequest req) {
        final String courseTitle = req.getParameter("title");
        final String course_status = req.getParameter("course_status");

        ErrorMessage errorMessage = new ErrorMessage();
        List<String> errors = new ArrayList<>();
        if (Objects.nonNull(courseTitle) && courseTitle.isEmpty()) {
            errors.add("Course title is empty");
        }
        final Optional<CourseStatus> courseStatus = CourseStatus.getCourseStatusValue(course_status);
        if (Objects.nonNull(courseStatus) && courseStatus.isEmpty()) {
            errors.add("Course ststus is incorrect");
        }
        errorMessage.setErrors(errors);
        return errorMessage;
    }
}
