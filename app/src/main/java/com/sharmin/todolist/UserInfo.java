package com.sharmin.todolist;

public class UserInfo {

    private String email, password;

    UserInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
