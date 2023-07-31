package com.example.baitaplon.database;

import androidx.annotation.NonNull;

import java.util.Date;

public class Comment {
    private int id;
    private String noidung;

    private double danhgia;
    private String ngaydang;
    private int id_user;
    private boolean active;
    private int id_qan;
    private QuanAn quanAn;

    public QuanAn getQuanAn() {
        return quanAn;
    }

    public void setQuanAn(QuanAn quanAn) {
        this.quanAn = quanAn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;


    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public double getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(double danhgia) {
        this.danhgia = danhgia;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId_qan() {
        return id_qan;
    }

    public void setId_qan(int id_qan) {
        this.id_qan = id_qan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return id_user + "\n" +"\t\t\t\t\t"+ noidung + "\t\t\t\t\t"+ngaydang;
    }
}
