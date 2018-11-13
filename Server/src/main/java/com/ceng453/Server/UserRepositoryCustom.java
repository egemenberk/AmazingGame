package com.ceng453.Server;


import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;


public interface UserRepositoryCustom {
    Map<String, String> authenticate(String username, String password) throws NoSuchAlgorithmException;

    List<Map<String, String>> getLeaderboardforAllTime();
    List<Map<String, String>> getLeaderboardfor7days();
}
