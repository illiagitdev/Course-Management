package com.courses.management.course;

import java.util.List;

public class Courses {
    private CourseRepository courseRepository;

    public Courses(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> showCourses() {
        return courseRepository.findAll();
    }

    public Course getById(Integer id) {
        final Course course = courseRepository.findById(id).get();
        return course;
    }

    public Course getByTitle (String title) {
        return courseRepository.getByTitle(title);
    }

    public Course createCourse(Course course) {
        courseRepository.save(course);
        return course;
    }
}
