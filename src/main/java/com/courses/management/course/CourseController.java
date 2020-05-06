package com.courses.management.course;

import com.courses.management.common.exceptions.ErrorMessage;
import com.courses.management.user.User;
import com.courses.management.user.UserRole;
import com.courses.management.user.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(path = "/course")
public class CourseController {
    private static final Logger LOG = LogManager.getLogger(CourseController.class);
    private Courses courses;
    private Users users;

    @Autowired
    public void setCourses(Courses courses, Users users){
        this.courses = courses;
        this.users = users;
    }

    @GetMapping("/showCourses")
    public String showCourses(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GrantedAuthority authority = userDetails.getAuthorities().iterator().next();
        if (authority.getAuthority().equals(UserRole.ROLE_ADMIN.getRole())){
            model.addAttribute("courses", courses.showCourses());
        } else {
            User user = users.getUser(userDetails.getUsername());
            if (Objects.nonNull(user.getCourse())) {
                model.addAttribute("courses", List.of(user.getCourse()));
            }
        }
        return "show-courses";
    }

    @GetMapping(path = "/get")
    public String getCourse(@RequestParam(name = "id") Integer id, Model model) {
        model.addAttribute("course", courses.getById(id));
        return "course-details";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

        try {
            courses.createCourse(course);
            model.addAttribute("message", course.getTitle());
            return "message";
        } catch (CourseAlreadyExistError e) {
            LOG.error(String.format("createCourse: course = %s", course.getTitle()));
            model.addAttribute("courseStatuses", CourseStatus.values());
            model.addAttribute("errors", List.of(new ErrorMessage("", e.getMessage())));
            return "create-course";
        }
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
