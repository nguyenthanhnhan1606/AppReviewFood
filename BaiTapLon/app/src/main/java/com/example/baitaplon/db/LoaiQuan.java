package com.example.baitaplon.db;

public class LoaiQuan {
    private int id;
    private String tenLoai;


    public LoaiQuan(int id, String tenLoai) {
        this.id = id;
        this.tenLoai = tenLoai;
    }

    public LoaiQuan() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
