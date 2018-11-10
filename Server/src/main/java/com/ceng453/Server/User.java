package com.ceng453.Server;


import javax.persistence.*;
import java.security.NoSuchAlgorithmException;

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

    @Column(nullable=false,name="password")
    private String password; // password that will be stored as encrypted

    @Column(name="sesion")
    private String session; // password that will be stored as encrypted

    public User() {}

    public User(java.lang.String username, java.lang.String email, java.lang.String password) {
        this.username = username;
        this.email = email;
        this.setPassword(password);
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            this.password = EncryptionHelper.encrypt(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}