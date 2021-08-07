package com.yang.mvc.common.vo;

import lombok.Data;

import java.util.List;

@Data
public class LoginSuccessUserInfoVo {
    private String username;
    private List<String> permissions;
}
