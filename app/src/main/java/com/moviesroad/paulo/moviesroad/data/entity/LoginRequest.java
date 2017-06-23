package com.moviesroad.paulo.moviesroad.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by paulo on 19/06/2017.
 */

public class LoginRequest {
    //@SerializedName("username")
    private String username;
    private String password;

    public LoginRequest withUsername(String username) {
        this.username = username;
        return this;
    }

    public LoginRequest withPassword(String password) {
        this.password = password;
        return  this;
    }
}
