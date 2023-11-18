package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    private final RoleService roleService;


    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/api/admin")
    public String getAdminPage(ModelMap model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<User> listOfUsers = userService.getAllUsers();
        List<Role> listRoles = roleService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("listOfUsers", listOfUsers);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("newUser", new User());

        return "admin";
    }

    @GetMapping("/api/user")
    public String getUserPage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }


    @PatchMapping("/users/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute("user") User user,
                           @RequestParam(name = "roles", required = false, defaultValue = "") List<Long> roleIds) {
        userService.update(user, roleIds);
        return "redirect:/api/admin";
    }


}