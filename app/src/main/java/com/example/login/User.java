package com.example.login;

public class User {
   private String id;
   private String password;
   private String username;

    public User(String id, String password, String username) {
        this.id = id;
        this.password = password;
        this.username = username;
    }

    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
