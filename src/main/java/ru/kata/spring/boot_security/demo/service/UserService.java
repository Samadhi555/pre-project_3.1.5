package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleService = roleService;
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

    @Transactional
    public void update(User user, List<Long> roleIds) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setAge(user.getAge());
        existingUser.setEmail(user.getEmail());

        List<Role> newRoles = roleService.getRolesByIds(roleIds);

        if (newRoles.stream().anyMatch(role -> "ROLE_ADMIN".equals(role.getName()))) {
            newRoles.clear();
            newRoles.addAll(roleService.findAll());
        }

        if (!newRoles.isEmpty()) {
            existingUser.getRoles().retainAll(newRoles);
        }

        for (Role role : newRoles) {
            if (!existingUser.getRoles().contains(role)) {
                existingUser.getRoles().add(role);
            }
        }
        userRepository.save(existingUser);
    }

    public void save(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return;
        }
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }

        if (user.getRoles().stream().anyMatch(role -> "ROLE_ADMIN".equals(role.getName()))) {
            List<Role> allRoles = roleService.findAll();
            user.setRoles(allRoles);
        } else {

            user.getRoles().forEach(role -> {
                Role foundRole = roleService.findByName(role.getName());
                if (foundRole != null) {
                    role.setId(foundRole.getId());
                }
            });
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<Role> getAllRoles() {
        return roleService.findAll();
    }

}