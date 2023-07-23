package com.example.baitaplon.database;

public class LoaiQuan {
    private int id;
    private String tenloai;

    public LoaiQuan(int id, String tenloai) {
        this.id = id;
        this.tenloai = tenloai;
    }

    public LoaiQuan() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    @Override
    public String toString() {
        return "LoaiQuan{" +
                "id=" + id +
                ", tenloai='" + tenloai + '\'' +
                '}';
    }
}
