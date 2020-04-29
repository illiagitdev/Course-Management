package com.courses.management.course;

import java.util.List;
import java.util.Objects;

public class Courses {
    private CourseRepository courseRepository;

    public Courses(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> showCourses() {
        return courseRepository.findAll();
    }

    public Course getById(Integer id) {
        return courseRepository.findById(id)
                .orElse(new Course());
    }

    public Course getByTitle (String title) {
        return courseRepository.getByTitle(title);
    }

    public Course createCourse(Course course) {
        if(Objects.isNull(getByTitle(course.getTitle()))) {
            throw new CourseAlreadyExistError(String.format("course with title%s already exists", course.getTitle()));
        }
        courseRepository.save(course);
        return course;
    }

    public List<String> getCourseTitles() {
        return courseRepository.getTitles();
    }
}
