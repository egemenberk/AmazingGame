package com.ceng453.Server;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

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
    private String password; // password that will be stored as encrypted

    @Column(name="sesion_id")
    private String session; // password that will be stored as encrypted

    @Column
    @CreationTimestamp
    private LocalDateTime createDateTime;

    @Column
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    // With orphanRemoval when we delete Score from the list It is deleted from database as well
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Score> scoreLogs;

    public User() {}

    public User(String username, String email, String password) {
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
/*
    public List<Score> getScoreLogs() {
        return scoreLogs;
    }
*/
}