package com.courses.management.user;

import java.util.List;

public interface UserService {

    List<User> showUsers();
    User findUser(Integer id);
    User getUser(String email);
    void create(User user);
    void update(Integer id, User userForm);
    void registerUser(User user);
    void delete(Integer id);

}
