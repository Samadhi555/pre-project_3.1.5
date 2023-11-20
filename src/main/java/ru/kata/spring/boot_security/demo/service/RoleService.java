package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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