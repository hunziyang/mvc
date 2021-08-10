package com.yang.mvc.service;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.yang.mvc.common.vo.LoginSuccessUserInfoVo;
import com.yang.mvc.common.vo.LoginUserInfoVo;
import com.yang.mvc.dao.UsersRepository;
import com.yang.mvc.entity.Users;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public void insert(LoginUserInfoVo loginUserInfoVo) {
        Users user = copyProperties(loginUserInfoVo);
        // todo 权限暂时默认处理
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        user.setRoles(roles);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        usersRepository.insert(user);
    }

    public LoginSuccessUserInfoVo loadUserByUsername(LoginUserInfoVo loginUserInfoVo) {
        Users loginUser = copyProperties(loginUserInfoVo);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("password", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Users> example = Example.of(loginUser, exampleMatcher);
        Users dbUser = usersRepository.findOne(example).orElse(null);
        if (ObjectUtils.isEmpty(dbUser)){
            return null;
        }
        LoginSuccessUserInfoVo loginSuccessUserInfoVo = new LoginSuccessUserInfoVo();
        BeanUtils.copyProperties(dbUser,loginSuccessUserInfoVo);
        return loginSuccessUserInfoVo;
    }

    private Users copyProperties(LoginUserInfoVo loginUserInfoVo) {
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        Users user = new Users();
        BeanUtils.copyProperties(loginUserInfoVo, user);
        user.setPassword(digester.digestHex(user.getPassword()));
        return user;
    }

}
