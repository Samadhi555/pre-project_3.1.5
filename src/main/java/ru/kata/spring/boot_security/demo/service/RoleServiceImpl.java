package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }
    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }
    @Override
    public Role showRoleById(Long id) {
        return roleRepository.getById(id);
    }
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
    @Override
    @PostConstruct
    public void initRoles() {
        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_ADMIN");
    }

    private void createRoleIfNotFound(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
    }

    @Override
    public List<Role> getRolesByIds(List<Long> roleIds) {
        return new ArrayList<>(roleRepository.findAllById(roleIds));
    }

    @Override
    public Set<Role> getRolesByNames(Set<String> roleNames) {
        return roleRepository.findByNameIn(roleNames);
    }


}