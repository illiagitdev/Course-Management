package com.courses.management.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class Users {
    private static final Logger LOG = LogManager.getLogger(Users.class);
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    public Users(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
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
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void update(Integer id, User userForm) {
        LOG.debug(String.format("update(id = %s): first+last name = %s + %s, email = %s, role=%s, status=%s",
                userForm.getId(), userForm.getFirstName(), userForm.getLastName(), userForm.getEmail(),
                userForm.getUserRole(), userForm.getStatus()));
        User user = findUser(id);
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setEmail(userForm.getEmail());
        user.setCourse(userForm.getCourse());
        user.setPassword(encoder.encode(user.getPassword()));
        if(Objects.nonNull(userForm.getStatus()) && Objects.nonNull(userForm.getUserRole())) {
            user.setStatus(userForm.getStatus());
            user.setUserRole(userForm.getUserRole());
        }
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

    public void delete(Integer id) {
        LOG.debug(String.format("delete: %s", id));
        userRepository.deleteById(id);
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
