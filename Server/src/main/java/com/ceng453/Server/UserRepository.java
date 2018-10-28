package com.ceng453.Server;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>, UserRepositoryCustom {

    List<User> findByToken(String token);
}

