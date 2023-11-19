package ru.kata.spring.boot_security.demo.dataLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class DataLoader implements CommandLineRunner {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DataLoader(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

@Override
public void run(String... args) {
    if (roleService.findAll().isEmpty()) {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        roleService.save(roleAdmin);
        roleService.save(roleUser);
    }

    List<String> adminRoles = roleService.findAll().stream().map(Role::getName).collect(Collectors.toList());
    List<String> userRoles = Collections.singletonList("ROLE_USER");

    User adminUser = new User();
    adminUser.setFirstname("Vasia");
    adminUser.setLastname("Petrov");
    adminUser.setAge((byte) 30);
    adminUser.setEmail("admin@mail.ru");
    adminUser.setPassword("admin");
    adminUser.setRoles(roleService.getRolesByNames(adminRoles));

    userService.save(adminUser);

    User regularUser = new User();
    regularUser.setFirstname("Masha");
    regularUser.setLastname("Ivanova");
    regularUser.setAge((byte) 25);
    regularUser.setEmail("user@mail.ru");
    regularUser.setPassword("user");
    regularUser.setRoles(roleService.getRolesByNames(userRoles));

    userService.save(regularUser);
}
}