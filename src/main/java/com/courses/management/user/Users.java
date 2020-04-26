package com.courses.management.user;

import com.courses.management.common.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Users {
    private static final Logger LOG = LogManager.getLogger(Users.class);
    private static final String EMAIL_REGEXP = "^[-a-z0-9!#$%&'*+/=?^_`{|}~]+(?:\\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@(?:" +
            "[a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\\.)*(?:aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|" +
            "museum|name|net|org|pro|tel|travel|[a-z][a-z])$";
    private UserRepository userRepository;

    public Users(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEXP);

    public static void validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            LOG.warn(String.format("validateEmail: email %s is incorrect", email));
            throw new IllegalArgumentException(String.format("Wrong email address %s.", email));
        }
    }

    public static void printUser(View view, User user) {
        view.write("User:");
        view.write(String.format("\t first name = %s", user.getFirstName()));
        view.write(String.format("\t last name = %s", user.getLastName()));
        view.write(String.format("\t email = %s", user.getEmail()));
        view.write(String.format("\t user role = %s", user.getUserRole()));
        view.write(String.format("\t user status = %s", user.getStatus()));
    }

    public void create(User user) {
        user.setStatus(UserStatus.NOT_ACTIVE);
        user.setUserRole(UserRole.NEWCOMER);
        userRepository.save(user);
    }

    public User findUser(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUser(Integer id) {
        return userRepository.findById(id).orElse(new User());
    }

    public void update(Integer id, User user) {
        //todo: not updating - exeption
        User update = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not fount"));
        update.setFirstName(user.getFirstName());
        update.setLastName(user.getLastName());
        update.setEmail(user.getEmail());
        update.setCourse(user.getCourse());
        userRepository.flush();
    }

    public List<User> showUsers() {
        return userRepository.findAll();
    }
}
