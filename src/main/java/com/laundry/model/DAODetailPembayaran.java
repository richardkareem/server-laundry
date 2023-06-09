package com.laundry.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "detail_pembayaran")
public class DAODetailPembayaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double jumlah_bayar;

    private String pilihan_pembayaran;



    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("detail_pembayaran")
    private DAOTransaksi daoTransaksi;

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

    public void setDaoTransaksi(DAOTransaksi daoTransaksi) {
        this.daoTransaksi = daoTransaksi;
    }


}
