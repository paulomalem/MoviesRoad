package com.moviesroad.paulo.moviesroad.data.entity;

/**
 * Created by paulo on 19/06/2017.
 */

public class UserEntity {
    private long id;
    private String username;
    private String name;
    private String role;
    private String accessToken;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
