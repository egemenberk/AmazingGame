package com.ceng453.Server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {

    List<User> findBySession(String token);

    @Transactional
    void deleteBySession(String session);
}

