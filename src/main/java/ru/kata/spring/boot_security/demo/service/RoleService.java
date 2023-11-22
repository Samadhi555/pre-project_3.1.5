package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;


public interface RoleService {

    public List<Role> findAll();

    public void save(Role role);

    public void deleteById(Long id);

    public Role showRoleById(Long id);


    public Role findByName(String name);


    public void initRoles();


    public List<Role> getRolesByIds(List<Long> roleIds);


    public Set<Role> getRolesByNames(Set<String> roleNames);



}