package com.yang.mvc.controller;

import com.yang.mvc.common.Result;
import com.yang.mvc.security.UsersContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public Result test(){
        String usersName = UsersContextHolder.getUsersName();
        System.out.println(usersName);
        return Result.success();
    }
}
