package com.laundry.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "detail_cucian")
@SQLDelete(sql = "UPDATE detail_cucian set is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class DAODetailCucian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long pembayaran;
    private String status;

    private String jenis_barang;
    private String berat_barang;
    private boolean is_deleted = Boolean.FALSE; // false = not yet deleted, true = deleted.


    /**
     * Detail Cucian -> trans */
    //Tabel Transaksi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_transaksi")
    @JsonIgnoreProperties("detail_cucian")
    DAOTransaksi daoTransaksi; //objek Transaksi //ibarat table transaksi
    // dikasih setter aja kl ga ntr ngaco
    public void setDaoTransaksi(DAOTransaksi daoTransaksi) {
        this.daoTransaksi = daoTransaksi;

    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(long pembayaran) {
        this.pembayaran = pembayaran;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJenis_barang() {
        return jenis_barang;
    }

    public void setJenis_barang(String jenis_barang) {
        this.jenis_barang = jenis_barang;
    }

    public String getBerat_barang() {
        return berat_barang;
    }

    public void setBerat_barang(String berat_barang) {
        this.berat_barang = berat_barang;
    }
}
