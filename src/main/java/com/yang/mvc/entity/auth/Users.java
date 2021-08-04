package com.yang.mvc.entity.auth;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "users")
@Data
public class Users {
    private ObjectId id;
    private String username;
    private String password;
    private List<String> permission;
    @Version
    private Integer revision;
    private String createdBy;
    private Date createdTime;
    private String updatedBy;
    private Date updatedTime;
}
