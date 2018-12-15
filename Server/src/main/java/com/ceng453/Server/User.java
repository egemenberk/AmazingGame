package com.ceng453.Server;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private String password; // password that will be stored as encrypted string

    @Column(name="sesion_id")
    private String session; // a session_token, randomlu generated per new login.
                            // Authotantication will be done using this token in every user based request
    @Column
    @CreationTimestamp
    private LocalDateTime createDateTime;

    @Column
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    // With orphanRemoval when we delete Score from the list It is deleted from database as well
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Score> scoreLogs = new HashSet<>();

    // Empty constructor, to create a token for newly registering users.
    // Other attributes will be set from UserController
    public User() {
        try {
            this.session = EncryptionHelper.generateToken();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // Constructor for contructing an user instance from @Requestbody data
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.setPassword(password);
        try {
            this.session = EncryptionHelper.generateToken();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // Custom setter for password to set user's password as encrypted
    public void setPassword(String password) {
        try {
            this.password = EncryptionHelper.encrypt(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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

    public String getSession() {
        return session;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }
}