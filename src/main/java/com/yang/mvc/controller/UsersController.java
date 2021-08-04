package com.yang.mvc.controller;

import com.yang.mvc.entity.auth.Users;
import com.yang.mvc.service.auth.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/users")
    public String users(){
        Users users = new Users();
        users.setUsername("17806171138");
        users.setPassword("123456");
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());
        usersService.insert(users);
        return "suceess";
    }
}
