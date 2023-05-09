package org.api2;

public class AuthReq {
    private String username;
    private String password;

    public AuthReq(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
