package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService {


    List<User> getAllUsers();

    User getUserById(Long id);


    User findByUsername(String username);


    void delete(Long id);


    void update(User user, List<Long> roleIds);


    void save(User user);

    public List<Role> getAllRoles();


}