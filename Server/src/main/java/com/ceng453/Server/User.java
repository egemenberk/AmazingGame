package com.ceng453.Server;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable=false,name="id")
    private Integer id;

    @Column(nullable=false,name="username", unique=true, length = 32)
    private String username;

    @Column(nullable=false,name="email", unique=true, length = 32)
    private String email;

    @Column(nullable=false,name="password_encrypted")
    private String password_encrypted; // password that will be stored as encrypted

    @Column(name="token")
    private String token; // password that will be stored as encrypted

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_encrypted() {
        return password_encrypted;
    }

    public void setPassword_encrypted(String password_encrypted) {
        try {
            this.password_encrypted = EncryptionHelper.encrypt(password_encrypted);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}