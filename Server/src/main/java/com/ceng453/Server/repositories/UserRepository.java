package com.ceng453.Server.repositories;

import com.ceng453.Server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {

    List<User> findBySession(String token);

    @Transactional
    void deleteBySession(String session);
}

