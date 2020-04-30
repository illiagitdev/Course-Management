package com.courses.management.user;

import java.util.List;

public class Users {
   private UserRepository userRepository;

    public Users(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> showUsers() {
        return userRepository.findAll();
    }

    public User findUser(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotExistException(String.format("User with id = %s not found", id)));
    }

        public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistException("User not found by specified email"));
    }

    public void create(User user) {
        user.setStatus(UserStatus.NOT_ACTIVE);
        user.setUserRole(UserRole.NEWCOMER);
        userRepository.save(user);
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
}
