package com.courses.management.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class Users {
    private static final Logger LOG = LogManager.getLogger(Users.class);
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    public Users(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
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
        if (emailExists(user.getEmail())) {
            throw new UserAlreadyExistsExeption(
                    String.format("There is an account with that email: %s", user.getEmail()));
        }
        LOG.debug(String.format("getUser: first+last name = %s + %s, email = %s",
                user.getFirstName(), user.getLastName(), user.getEmail()));
        user.setStatus(UserStatus.NOT_ACTIVE);
        user.setUserRole(UserRole.ROLE_NEWCOMER);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void update(Integer id, User user) {
        LOG.debug(String.format("update(id = %s): first+last name = %s + %s, email = %s",
                id, user.getFirstName(), user.getLastName(), user.getEmail()));
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void registerUser(User user) {
        if (emailExists(user.getEmail())) {
            throw new UserAlreadyExistsExeption(
                    String.format("There is an account with that email: %s", user.getEmail()));
        }
        user.setUserRole(UserRole.ROLE_NEWCOMER);
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
