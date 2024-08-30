package com.amazoonS3.mini.model;

import java.io.Serializable;

public class User implements Serializable {
    
    private static final long serialVersionUID = 1L; // 권장 사항

    private String username;
    private String nickname;
    private String email;
    private String password;
    private String idtoken;

    // Getters and Setters

    public String getIdtoken() {
        return this.idtoken;
    }

    public void setIdtoken(String idtoken) {
        this.idtoken = idtoken;
    }
    
    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
