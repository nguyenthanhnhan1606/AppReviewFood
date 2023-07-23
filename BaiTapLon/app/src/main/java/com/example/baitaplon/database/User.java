package com.example.baitaplon.database;

public class User {
    private int id;
    private String hoten;
    private String username;
    private String password;
    private String email;
    private String sdt;
    private String avatar;
    private boolean active;
    private String user_role;

    public User(int id, String hoten, String username, String password, String email, String sdt, String avatar, boolean active, String user_role) {
        this.id = id;
        this.hoten = hoten;
        this.username = username;
        this.password = password;
        this.email = email;
        this.sdt = sdt;
        this.avatar = avatar;
        this.active = active;
        this.user_role = user_role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}
