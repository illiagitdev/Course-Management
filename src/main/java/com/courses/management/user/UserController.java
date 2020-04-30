package com.courses.management.user;

import com.courses.management.course.Courses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    private static final Logger LOG = LogManager.getLogger(UserController.class);
    private Users users;
    private Courses courses;

    public void setUsers(Users users) {
        this.users = users;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    @GetMapping("/get")
    public String getUser(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("userDetails", users.findUser(id));
        return "user-details";
    }

    @GetMapping(path = "/showUsers")
    public String showUsersByCourseView(Model model) {
        model.addAttribute("users", users.showUsers());
        return "show-users";
    }

    @GetMapping(path = "/findUsers")
    public String showFindUserPage() {
        return "find-user";
    }

    @GetMapping(path = "/find")
    public String findUser(@RequestParam("email") String email, Model model) {
        User user = null;
        try {
            user = users.getUser(email);
        } catch (UserNotExistException ex) {
            LOG.error("findUser: ", ex);
            model.addAttribute("error", ex.getMessage());
            return "find-user";
        }
        model.addAttribute("userDetails", user);
        return "user-details";
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

    @ModelAttribute("user")
    public User getDefaultUser() {
        return new User();
    }
}
