//package ru.kata.spring.boot_security.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.service.RoleService;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//import java.security.Principal;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Controller
//public class AdminController {
//    private final UserService userService;
//    private final RoleService roleService;
//
//    @Autowired
//    public AdminController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//
//    @PostMapping("/admin")
//    public String createNewUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIds) {
//        user.setUsername(user.getEmail());
//        userService.save(user, roleIds);
//        return "redirect:/admin";
//    }
//
//
//    @GetMapping("/admin")
//    public String getAdminPage(ModelMap model, Principal principal) {
//        User user = userService.findByUsername(principal.getName());
//        List<User> listOfUsers = userService.getAllUsers();
//        List<Role> listRoles = roleService.findAll();
//
//        model.addAttribute("user", user);
//        model.addAttribute("listOfUsers", listOfUsers);
//        model.addAttribute("listRoles", listRoles);
//        model.addAttribute("newUser", new User());
//
//        return "admin";
//    }
//
//
//    @GetMapping("/user/{id}")
//    public String getUserPage(@PathVariable("id") Long id, Model model) {
//        User user = userService.getUserById(id);
//        model.addAttribute("user", user);
//        return "user";
//    }
//
//
//    @DeleteMapping("/admin/delete/{id}")
//    public String deleteUserById(@PathVariable("id") Long id) {
//        userService.delete(id);
//        return "redirect:/admin";
//    }
//
//
//
//    @RequestMapping(value = "/admin/edit/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
//    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id,
//                             @RequestParam(name = "roles", required = false, defaultValue = "") List<Long> roleIds) {
//        userService.update(user, roleIds);
//        return "redirect:/admin";
//    }
//
//
//    @GetMapping("/user/{id}/edit")
//    public String getEditPage(Model model, @PathVariable("id") Long id) {
//        User user = userService.getUserById(id);
//        List<Role> listRoles = roleService.findAll();
//        List<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
//        model.addAttribute("user", user);
//        model.addAttribute("listRoles", listRoles);
//        model.addAttribute("roleIds", roleIds);
//        return "admin";
//    }
//
//}