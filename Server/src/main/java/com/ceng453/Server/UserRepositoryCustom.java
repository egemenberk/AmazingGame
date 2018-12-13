package com.ceng453.Server;


import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;


public interface UserRepositoryCustom {
    ResponseEntity<String> authenticate(String username, String password) throws NoSuchAlgorithmException;

    List<Map<String, String>> getLeaderboardforAllTime();
    List<Map<String, String>> getLeaderboardfor7days();
}
