package com.laundry.modelDTO;

public class DetailPembayaranDTO {

    private double jumlah_bayar;

    private String pilihan_pembayaran;

    public double getJumlah_bayar() {
        return jumlah_bayar;
    }

    public void setJumlah_bayar(double jumlah_bayar) {
        this.jumlah_bayar = jumlah_bayar;
    }

    public String getPilihan_pembayaran() {
        return pilihan_pembayaran;
    }

    public void setPilihan_pembayaran(String pilihan_pembayaran) {
        this.pilihan_pembayaran = pilihan_pembayaran;
    }
}
