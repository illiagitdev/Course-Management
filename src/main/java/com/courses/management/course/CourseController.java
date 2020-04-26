package com.courses.management.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/course/*")
public class CourseController {
    private Courses courses;

    @Autowired
    public void setCourses(Courses courses){
        this.courses = courses;
    }

    @GetMapping("/showCourses")
    public String showCourses(Model model) {
        model.addAttribute("courses", courses.showCourses());
        return "show-courses";
    }

    @GetMapping(path = "/get")
    public String getCourse(@RequestParam(name = "id") Integer id, Model model) {
        model.addAttribute("course", courses.getById(id));
        return "course-details";
    }

    @GetMapping(path = "/createCourses")
    public String getCreateCourseView (Model model) {
        model.addAttribute("courseStatuses", CourseStatus.values());
        return "create-course";
    }

    @PostMapping(path = "/createCourse")
    public String createCourse(@ModelAttribute("course") @Valid Course course, BindingResult result, Model model) {
        System.out.println("Hello create course start");
         if (result.hasErrors()) {
             model.addAttribute("courseStatuses", CourseStatus.values());
             return "create-course";
         }

         courses.createCourse(course);
         model.addAttribute("message", course.getTitle());
         return "message";
    }

    @GetMapping(path = "/findCourseView")
    public String findCourseView(){
        return "find-course";
    }

    @GetMapping(path = "/findCourse")
    public String findCourse(@RequestParam("courseName") String courseName, Model model){
        Course course = courses.getByTitle(courseName);
        if (course.getTitle().equals("")){
            return "find-course";
        }
        model.addAttribute("course", course);
        return "course-details";
    }

    @ModelAttribute("course")
    public Course getDefaultCourse() {
        return new Course();
    }
}
