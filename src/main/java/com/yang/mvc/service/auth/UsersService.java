package com.yang.mvc.service.auth;

import com.yang.mvc.dao.auth.UsersRepository;
import com.yang.mvc.entity.auth.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public void insert(Users users){
        usersRepository.insert(users);
    }
}
