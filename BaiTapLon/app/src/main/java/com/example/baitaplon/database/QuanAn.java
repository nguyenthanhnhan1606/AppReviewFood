package com.example.baitaplon.database;

public class QuanAn {
    private int id;
    private String tenquan;
    private String diadiem;
    private double danhgia;
    private int hinhanh;
    private boolean active;
    private int id_loai;

    public QuanAn(int id, String tenquan, String diadiem, double danhgia, int hinhanh, boolean active, int id_loai) {
        this.id = id;
        this.tenquan = tenquan;
        this.diadiem = diadiem;
        this.danhgia = danhgia;
        this.hinhanh = hinhanh;
        this.active = active;
        this.id_loai = id_loai;
    }

    public QuanAn() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenquan() {
        return tenquan;
    }

    public void setTenquan(String tenquan) {
        this.tenquan = tenquan;
    }

    public String getDiadiem() {
        return diadiem;
    }

    public void setDiadiem(String diadiem) {
        this.diadiem = diadiem;
    }

    public double getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(double danhgia) {
        this.danhgia = danhgia;
    }

    public int getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(int hinhanh) {
        this.hinhanh = hinhanh;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId_loai() {
        return id_loai;
    }

    public void setId_loai(int id_loai) {
        this.id_loai = id_loai;
    }

    @Override
    public String toString() {
        return "QuanAn{" +
                "id=" + id +
                ", tenquan='" + tenquan + '\'' +
                ", diadiem='" + diadiem + '\'' +
                ", danhgia=" + danhgia +
                ", hinhanh='" + hinhanh + '\'' +
                ", active=" + active +
                ", id_loai=" + id_loai +
                '}';
    }
}
