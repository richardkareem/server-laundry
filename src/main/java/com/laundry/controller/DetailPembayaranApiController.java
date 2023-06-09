package com.laundry.controller;

import com.laundry.model.DAODetailCucian;
import com.laundry.model.DAODetailPembayaran;
import com.laundry.model.DAOTransaksi;
import com.laundry.modelDTO.DetailPembayaranDTO;
import com.laundry.repository.PembayaranRepo;
import com.laundry.repository.TransRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/pembayaran")
public class DetailPembayaranApiController {
    public static final Logger logger = LoggerFactory.getLogger(DetailPembayaranApiController.class);

    @Autowired
    PembayaranRepo pembayaranRepo;

    @Autowired
    TransRepo transRepo;

    @RequestMapping(value = "/trans/{idTransaksi}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> createTrans (@PathVariable long idTransaksi,
                                          @RequestBody DAODetailPembayaran pembayaran) throws SQLException, ClassNotFoundException {

        //Parent nya transaksi, idTrans (PK)
        //childnya pembayaran , id(PK), fk_id_transaksi(FK)

        DAOTransaksi transaksi = transRepo.findById(idTransaksi).orElse(null);//pencarian berdasarkan id Di table transaksi

        pembayaran.setDaoTransaksi(transaksi);//memasukkan fk yg ada di transaksi ke pembayaran
        transaksi.setDaoDetailPembayaran(pembayaran);//setDaoDetailPembayaran method yg ada di transaksi
        transRepo.save(transaksi);//karena transaksi parentnya make transrepo bukan pembayaranrepo

        return new ResponseEntity<>(pembayaran, HttpStatus.CREATED);

    }


    /**
     ================================== Update Detail Pembayaran by ID ======================================
     */
    //INI NTR DULU DAH

    @RequestMapping(value = "/detail-pembayaran/{id}", method = RequestMethod.PUT)
    public ResponseEntity <?> updateDetailPembayaran (@PathVariable("id") long id, @RequestBody DAODetailPembayaran pembayaran) throws SQLException, ClassNotFoundException {

        logger.info("Updating Detail pembayaran with {}", id);


////        if (currentProduct == null) {
////            logger.error("Unable to update. Product with id {} not found.", id);
////            return new ResponseEntity<>(new CustomErrorType("Unable to upate. Product with id " + id + " not found."),
////                    HttpStatus.NOT_FOUND);
////        }
        DAODetailPembayaran payment = pembayaranRepo.findById(id).orElse(null);
        DAOTransaksi transaksi = transRepo.findById(id).orElse(null);

        pembayaran.setDaoTransaksi(transaksi);
        pembayaran.setPilihan_pembayaran(pembayaran.getPilihan_pembayaran());
        pembayaran.setJumlah_bayar(pembayaran.getJumlah_bayar());


        transaksi.setDaoDetailPembayaran(pembayaran);
        transRepo.save(transaksi);

        return new ResponseEntity<>(transRepo, HttpStatus.OK);
    }
//
}
