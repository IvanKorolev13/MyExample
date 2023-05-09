package org.api2;

public class AuthRes {
    private String token;

    public AuthRes(String token) {
        this.token = token;
    }

    public AuthRes() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
