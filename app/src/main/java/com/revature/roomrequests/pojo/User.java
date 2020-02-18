package com.revature.roomrequests.pojo;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String role;
    private Integer id;
    private Integer token;

    public User() { }

    public User(String username, String role, Integer id, Integer token) {
        this.username = username;
        this.role = role;
        this.id = id;
        this.token = token;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getToken() { return token; }

    public void setToken(Integer token) { this.token = token; }
}
