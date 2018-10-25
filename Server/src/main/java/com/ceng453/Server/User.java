package com.ceng453.Server;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(nullable=false)
    private String username;
    @Column(nullable=false)
    private String email;
    @Column(nullable=false)
    private String password_encrypted; // password that will be stored as encrypted


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
            MessageDigest message_digest = MessageDigest.getInstance("SHA");
            byte[] digested_bytes = message_digest.digest(password_encrypted.getBytes());
            StringBuffer hex_string = new StringBuffer();
            for(int i=0; i<digested_bytes.length; i++){
                hex_string.append(Integer.toHexString(0xff & digested_bytes[i]));
            }
            this.password_encrypted = hex_string.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}