package com.courses.management.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("select c from Course c where c.title=?1")
    Course getByTitle(String title);

    @Query("select c.title from Course c")
    List<String> getTitles();
}
