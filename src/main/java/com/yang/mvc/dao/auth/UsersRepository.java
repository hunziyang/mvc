package com.yang.mvc.dao.auth;

import com.yang.mvc.entity.auth.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<Users, ObjectId> {
}
