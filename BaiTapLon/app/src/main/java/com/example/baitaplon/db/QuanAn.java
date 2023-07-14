package com.example.baitaplon.db;



public class QuanAn {
    private int id;
    private String tenQuan;
    private String diaDiem;
    private String hinhAnh;
    private double danhGia;
    private int id_loaiQuan;

    public QuanAn(int id, String tenQuan, String diaDiem, String hinhAnh, double danhGia, int id_loaiQuan) {
        this.id = id;
        this.tenQuan = tenQuan;
        this.diaDiem = diaDiem;
        this.hinhAnh = hinhAnh;
        this.danhGia = danhGia;
        this.id_loaiQuan = id_loaiQuan;
    }

    public QuanAn() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenQuan() {
        return tenQuan;
    }

    public void setTenQuan(String tenQuan) {
        this.tenQuan = tenQuan;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public Double getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(Double danhGia) {
        this.danhGia = danhGia;
    }

    public int getId_loaiQuan() {
        return id_loaiQuan;
    }

    public void setId_loaiQuan(int id_loaiQuan) {
        this.id_loaiQuan = id_loaiQuan;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", tenQuan='" + tenQuan + '\'' +
                ", diaDiem='" + diaDiem + '\'' +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", danhGia=" + danhGia +
                ", id_loaiQuan=" + id_loaiQuan;
    }
}
