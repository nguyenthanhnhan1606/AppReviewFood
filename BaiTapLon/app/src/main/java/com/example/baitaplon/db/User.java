package com.example.baitaplon.db;

public class User {
    private int id;
    private String hoTen;
    private String userName;
    private String passWord;
    private String email;
    private String sdt;
    private String avatar;
    private int user_role;
    private boolean active;

    public User(int id, String hoTen, String userName, String passWord, String email, String sdt, String avatar, int user_role, boolean active) {
        this.id = id;
        this.hoTen = hoTen;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.sdt = sdt;
        this.avatar = avatar;
        this.user_role = user_role;
        this.active = active;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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

    public int getUser_role() {
        return user_role;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
