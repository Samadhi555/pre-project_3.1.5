package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    private final RoleService roleService;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder, RoleRepository roleRepository, RoleService roleService, RoleRepository roleRepository1, RoleService roleService1) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository1;
        this.roleService = roleService1;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NullPointerException("Значение в базе данных не найдено"));
    }


    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Пользователь %s не найден!", username));
        }
        return user;
    }


    public void delete(Long id) {
        userRepository.deleteById(id);
    }


//    public void update(User user, List<Long> roleIds) {
//        User existingUser = userRepository.findById(user.getId())
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));
//
//        existingUser.setFirstname(user.getFirstname());
//        existingUser.setLastname(user.getLastname());
//        existingUser.setAge(user.getAge());
//        existingUser.setEmail(user.getEmail());
//
//        Set<Role> existingRoles = existingUser.getRoles();
//        Set<Role> newRoles = roleService.getRolesByIds(roleIds);
//
//        existingRoles.removeIf(role -> role.getName().equals("ROLE_ADMIN"));
//
//        if (newRoles.stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN")) && existingRoles.stream().noneMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
//            existingRoles.add(roleService.findByName("ROLE_ADMIN"));
//        }
//        userRepository.save(existingUser);
//    }


    public void update(User user, List<Long> roleIds) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setAge(user.getAge());
        existingUser.setEmail(user.getEmail());

        Set<Role> newRoles = roleService.getRolesByIds(roleIds);
        existingUser.setRoles(newRoles);

        userRepository.save(existingUser);
    }


    @Transactional
    public void save(User user, List<Long> roleIds) {
        if (getAllUsers().contains(user)) {
            return;
        }

        boolean containsUserRole = roleIds.stream()
                .anyMatch(roleId -> roleId != null && roleId.equals(roleService.findByName("ROLE_USER").getId()));

        if (!containsUserRole) {
            roleIds.add(roleService.findByName("ROLE_USER").getId());
        }

        Set<Role> roles = roleService.getRolesByIds(roleIds);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}