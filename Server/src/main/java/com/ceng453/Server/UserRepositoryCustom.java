package com.ceng453.Server;


import java.security.NoSuchAlgorithmException;
import java.util.Map;


public interface UserRepositoryCustom {
    Map<String, String> authenticate(String username, String password) throws NoSuchAlgorithmException;
}
