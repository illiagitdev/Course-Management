package com.courses.management.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Users {
    private static final Logger LOG = LogManager.getLogger(Users.class);
   private UserRepository userRepository;

    public Users(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> showUsers() {
        LOG.debug("showUsers:");
        return userRepository.findAll();
    }

    public User findUser(Integer id) {
        LOG.debug(String.format("findUser: id = %s", id));
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotExistException(String.format("User with id = %s not found", id)));
    }

        public User getUser(String email) {
        LOG.debug(String.format("getUser: email = %s", email));
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistException("User not found by specified email"));
    }

    public void create(User user) {
        LOG.debug(String.format("getUser: first+last name = %s + %s, email = %s",
                user.getFirstName(), user.getLastName(), user.getEmail()));
        user.setStatus(UserStatus.NOT_ACTIVE);
        user.setUserRole(UserRole.NEWCOMER);
        userRepository.save(user);
    }

    public void update(Integer id, User user) {
        LOG.debug(String.format("update(id = %s): first+last name = %s + %s, email = %s",
                id, user.getFirstName(), user.getLastName(), user.getEmail()));
        //todo: not updating - exeption
        User update = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not fount"));
        update.setFirstName(user.getFirstName());
        update.setLastName(user.getLastName());
        update.setEmail(user.getEmail());
        update.setCourse(user.getCourse());
        userRepository.flush();
    }
}
