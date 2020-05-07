package com.courses.management.user;

import com.courses.management.course.Courses;
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

@Controller
@RequestMapping(path = "/user")
public class UserController {
    private static final Logger LOG = LogManager.getLogger(UserController.class);
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

    @GetMapping("/get")
    public String getUser(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("userDetails", users.findUser(id));
        return "user-details";
    }

    @GetMapping(path = "/showUsers")
    public String showUsersByCourseView(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GrantedAuthority authority = userDetails.getAuthorities().iterator().next();
       if (authority.getAuthority().equals(UserRole.ROLE_ADMIN.getRole())) {
            model.addAttribute("users", users.showUsers());
        } else {
            User user = users.getUser(userDetails.getUsername());
            model.addAttribute("users", List.of(user));
        }
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

    @GetMapping("/registration")
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping(path = "/registration")
    public String registerUser(@ModelAttribute("userForm") @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }

        try {
            users.registerUser(user);
        } catch (UserAlreadyExistsExeption ex) {
            LOG.error(String.format("registerUser: error %s", ex.getMessage()), ex);
            model.addAttribute("message", "An account for this username already exists.");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping(path = "/userDetails/{username:.+}")
    public String showPrincipalDetails(@PathVariable("username") String username, Model model) {
        model.addAttribute("userDetails", users.getUser(username));
        return "user-details";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/create")
    public String createUserView(Model model) {
        model.addAttribute("userRoles", UserRole.values());
        model.addAttribute("statuses", UserStatus.values());
        model.addAttribute("courses", courses.getCourseTitles());
        return "create-user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/create")
    public String createUser(@ModelAttribute("userForm") @Valid User user, @RequestParam("courseName") String courseName,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userRoles", UserRole.values());
            model.addAttribute("statuses", UserStatus.values());
            model.addAttribute("courses", courses.getCourseTitles());
            return "create-user";
        }

        try {
            user.setCourse(courses.getByTitle(courseName));
            users.create(user);
        } catch (UserAlreadyExistsExeption ex) {
            LOG.error(String.format("createUser: error %s", ex.getMessage()), ex);
            model.addAttribute("message", "An account for this username already exists.");
            return "registration";
        }

        model.addAttribute("userDetails", user);
        return "user-details";
    }

    @GetMapping(path = "/updateUser")
    public String updateUserView(@RequestParam("id") Integer id, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GrantedAuthority authority = userDetails.getAuthorities().iterator().next();
        model.addAttribute("user", users.findUser(id));
        model.addAttribute("courses", courses.getCourseTitles());

        if (authority.getAuthority().equals(UserRole.ROLE_ADMIN.getRole())) {
            model.addAttribute("userRoles", UserRole.values());
            model.addAttribute("statuses", UserStatus.values());
        }

        return "user-update";
    }

    @PostMapping(path = "/updateUser")
    public String updateUser(@ModelAttribute("userForm") User userForm, @RequestParam("courseName") String courseName,
                             @RequestParam("id") Integer id, BindingResult result, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GrantedAuthority authority = userDetails.getAuthorities().iterator().next();
        if (result.hasErrors()) {
            model.addAttribute("userForm", users.findUser(id));
            model.addAttribute("courses", courses.getCourseTitles());
            if (authority.getAuthority().equals(UserRole.ROLE_ADMIN.getRole())) {
                model.addAttribute("userRoles", UserRole.values());
                model.addAttribute("statuses", UserStatus.values());
            }
            return "user-update";
        }
        userForm.setCourse(courses.getByTitle(courseName));
        users.update(id, userForm);
        model.addAttribute("userDetails", users.findUser(id));
        return "user-details";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/delete")
    public String deleteUser(@RequestParam("id") Integer id, Model model) {
        users.delete(id);
        model.addAttribute("message", "User was deleted!");
        return "message";
    }

    @ModelAttribute("userForm")
    public User getDefaultUser() {
        return new User();
    }
}
