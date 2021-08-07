package com.yang.mvc.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "users")
@Data
public class Users {
    @Id
    private ObjectId id;
    private String username;
    private String password;
    private List<String> permissions;
    @Version
    private Integer revision;
    private Date createdTime;
    private Date updatedTime;

    public static void main(String[] args) {
        Users users = new Users();
        users.setUsername("17806171138");
        users.setPassword("123456");
        System.out.println(JSON.toJSONString(users));
    }
}
