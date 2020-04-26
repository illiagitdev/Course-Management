package com.courses.management.user;

import com.courses.management.course.Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/user/*")
public class UserController {
    private Users users;
    private Courses courses;

    @Autowired
    public void setUsers(Users users) {
        this.users = users;
    }

    @Autowired
    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    @GetMapping(path = "/create")
    public String createUserView(Model model) {
        model.addAttribute("courses", courses.getCourseTitles());
        return "create-user";
    }

    @PostMapping(path = "/create")
    public String createUser(@ModelAttribute("user") @Valid User user, @RequestParam("courseName") String courseName,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courses.getCourseTitles());
            return "create-user";
        }
        user.setCourse(courses.getByTitle(courseName ));
        users.create(user);
        model.addAttribute("userDetails", user);
        return "user-details";
    }

    @GetMapping("/get")
    public String getUser(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("userDetails", users.findUser(id));
        return "user-details";
    }

    @GetMapping(path = "/updateUser")
    public String updateUserView(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("userDetails", users.findUser(id));
        model.addAttribute("courses", courses.getCourseTitles());
        return "user-update";
    }

    @PostMapping(path = "/updateUser")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("courseName") String courseName,
                             @RequestParam("id") Integer id, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userDetails", users.findUser(id));
            model.addAttribute("courses", courses.getCourseTitles());
            return "user-update";
        }
        users.update(id, user);
        model.addAttribute("userDetails", users.findUser(id));
        return "user-details";
    }

    @GetMapping(path = "/showUsers")
    public String showUsersByCourseView(Model model) {
        model.addAttribute("users", users.showUsers());
        return "show-users";
    }

    @ModelAttribute("user")
    public User getDefaultUser() {
        return new User();
    }
}
