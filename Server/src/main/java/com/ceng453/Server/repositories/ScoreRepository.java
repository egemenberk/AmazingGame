package com.ceng453.Server.repositories;

import com.ceng453.Server.entities.Score;
import com.ceng453.Server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {
    List<Score> findByUser(User user);
}
