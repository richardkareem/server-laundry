package com.laundry.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mysql.cj.x.protobuf.MysqlxCursor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transaksi")
@SQLDelete(sql = "UPDATE transaksi SET is_deleted = true where id_trans=?")
@Where(clause = "is_deleted = false")
public class DAOTransaksi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTrans;

    public String getDate() {
        return date;
    }

    private String date;

    private boolean pilihan_Kurir;

    private boolean isDeleted = Boolean.FALSE; //false = not yet deleted, true = deleted.



    // one to one user ke trans
    //user parent
    //trans child
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @JsonIgnoreProperties("transaksi")
    private DAOUser user;

    public void setUser(DAOUser user) {
        this.user = user;
     }

    public boolean isPilihan_Kurir() {
        return pilihan_Kurir;
    }

    public void setPilihan_Kurir(boolean pilihan_Kurir) {
        this.pilihan_Kurir = pilihan_Kurir;
    }

    public long getIdTrans() {
        return idTrans;
    }

    public void setIdTrans(int idTrans) {
        this.idTrans = idTrans;
    }

    public String getDate(String date) {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }


   /** Transaksi ke detail_cucian  */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "daoTransaksi", cascade = CascadeType.ALL, orphanRemoval = true)//daoTransaksi
    @JsonIgnoreProperties("transaksi")
    // mappedBy = -> "daoTransaksi" ngambil dari properti DAOTransaksi daoTransaki yg ada di DAODetailCucian
     private  List <DAODetailCucian> daoDetailCucians = new ArrayList<>();

    //method addDetailCucian
    public void addDetCucian (DAODetailCucian daoDetailCucian){
        daoDetailCucians.add(daoDetailCucian);
        daoDetailCucian.setDaoTransaksi(this);
    }

    public void deleteDetCucian (DAODetailCucian daoDetailCucian){
        daoDetailCucians.remove(daoDetailCucian);
    }

   /* public DAOTransaksi(){ <- gini doang bisa bikin error wtf

    }*/

    public List<DAODetailCucian> getDaoDetailCucians() {
        return daoDetailCucians;
    }

    public void setDaoDetailCucians(List<DAODetailCucian> daoDetailCucians) {
        this.daoDetailCucians = daoDetailCucians;
    }

    /** Trans ke Det_pembayaran **/
    // Transaksi FK
    // Detail Pembayaran PK
    @OneToOne(mappedBy = "daoTransaksi", cascade = CascadeType.ALL) // mapped ke table transaksi
    @JoinColumn(name = "fk_id_pembayaran")
    @JsonIgnoreProperties("transaksi")
    private DAODetailPembayaran daoDetailPembayaran;

    public void setDaoDetailPembayaran(DAODetailPembayaran daoDetailPembayaran) {
        this.daoDetailPembayaran = daoDetailPembayaran;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

}
