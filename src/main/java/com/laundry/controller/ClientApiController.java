package com.laundry.controller;

import com.laundry.config.CustomErrorType;
import com.laundry.model.*;
import com.laundry.repository.*;
import com.laundry.service.DetailCucianService;
import com.laundry.service.TransaksiService;
import com.laundry.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/client")
public class ClientApiController {
    public static final Logger logger = LoggerFactory.getLogger(ClientApiController.class);

    @Autowired
    private UserRepo userRepository;
//
    @Autowired
    private TransRepo transRepo;
//
    @Autowired
    private DetailCucianRepo detailCucianRepo;

    @Autowired
    private PembayaranRepo pembayaranRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private  TransaksiService transaksiService;
    @Autowired
    private UserService userService;
    @Autowired
    private DetailCucianService detailCucianService;



    /** ================================== Create Trans ====================================== */
    @RequestMapping(value = "/trans/{id_user}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> createTrans (@PathVariable("id_user") long id_user,
                                          @RequestBody Map<String, Object> payload) throws SQLException, ClassNotFoundException {

        DAOUser user = transaksiService.findById(id_user);//ambil id user
        String role = user.getRole();

        if (role.equalsIgnoreCase("client")) {

            //Create Transaksi Fields
            DAOTransaksi transcation = new DAOTransaksi();
            transcation.setUser(user);
            transcation.setDate(payload.get("date").toString());
            transcation.setPilihan_Kurir(Boolean.parseBoolean(payload.get("pilihan_kurir").toString()));

            //Create Detail Transaksi Fields
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> detailCucian = (List<Map<String, Object>>) payload.get("detail_cucian");
            for (Map<String, Object> detailCucianObj : detailCucian) {

                DAODetailCucian detail = new DAODetailCucian();
                detail.setPembayaran(Long.parseLong(detailCucianObj.get("pembayaran").toString()));
                detail.setStatus(detailCucianObj.get("status").toString());
                detail.setBerat_barang(detailCucianObj.get("berat_barang").toString());
                detail.setJenis_barang(detailCucianObj.get("jenis_barang").toString());

                transcation.addDetCucian(detail);

            }

            transRepo.save(transcation);
            return new ResponseEntity<>(transcation, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(new CustomErrorType("Anda tidak Dapat mengakses fitur ini "),HttpStatus.FORBIDDEN);
        }
    }

/**
    ================================== Get ALl User ======================================//
*/
    @RequestMapping(value = "/users/", method = RequestMethod.GET, produces="application/json")
        public ResponseEntity<List<DAOUser>> listAllProducts() {


        List <DAOUser> users = userRepository.findAll();;

        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    /**
     ================================== Get ALl Transaction by ID  ====================================== //
     */
    @RequestMapping(value = "/alltrans/{id_user}", method = RequestMethod.GET, produces="application/json")
        public ResponseEntity<?> listAllTransaction(
        @PathVariable("id_user") long id_user)throws SQLException, ClassNotFoundException {
        DAOUser user = userService.findUserById(id_user);

//        String role = userService.findRole(id_user);

            if (user == null ){
                logger.error("Product with id {} not found.", id_user);
                return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
            }
//            logger.info("Get Transaction with ");
            return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    /**
//     ================================== Get ALl Transaction unfinished by ID  ====================================== //
//     */
//    @RequestMapping(value = "/unfinishedTrans/{id_user}", method = RequestMethod.GET, produces="application/json")
//    public ResponseEntity<?> listAllTransactionunfinished(
//            @PathVariable("id_user") long id_user)throws SQLException, ClassNotFoundException {
//        DAOUser user = userService.findUserById(id_user);
//        DAODetailCucian detail = (DAODetailCucian) detailCucianRepo.findAll();
//
//
//
////        String role = userService.findRole(id_user);
//
//        if (user == null ){
//            logger.error("Product with id {} not found.", id_user);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//        }
//           detail.getStatus().equalsIgnoreCase("Sedang Menjemput Baju");
//
//            return new ResponseEntity<>(user, HttpStatus.OK);
//
////            logger.info("Get Transaction with ");
//
//    }


    /**
     * ================================== Get All Detail Cucian ======================================
     */
    @RequestMapping(value = "/alldetailcucian", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<List<DAODetailCucian>> listAllDetailCucian () {

        List <DAODetailCucian> detailCucians = detailCucianRepo.findAll();
//        if(detailCucians.)


        return new ResponseEntity<>(detailCucians, HttpStatus.OK);
    }

    /**
     ================================== Update Transaction by ID ======================================
     */
    @RequestMapping(value = "/transaction/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatetransaksi (@PathVariable("id") long id, @RequestBody DAOTransaksi daoTransaksi) throws SQLException, ClassNotFoundException {

        logger.info("Updating Product with id {}", id);
        DAOTransaksi currentTrans = transRepo.findById(id).orElse(null);

        if (currentTrans== null) {
            logger.error("Unable to update. Product with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to update. Product with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        currentTrans.setDate(daoTransaksi.getDate());
        currentTrans.setPilihan_Kurir(daoTransaksi.isPilihan_Kurir());

        transRepo.save(currentTrans);

        return new ResponseEntity<>(currentTrans, HttpStatus.OK);
    }

    /**
     ================================== Update User by ID ======================================
     */
    @RequestMapping(value = "/updateuser/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody DAOUser user) throws SQLException, ClassNotFoundException {

        logger.info("Updating Product with id {}", id);
        DAOUser currentUser = userRepo.findById(id).orElse(null);

        if (currentUser == null) {
            logger.error("Unable to update. Product with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to update User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        currentUser.setImg(user.getImg());

        userRepo.save(currentUser);

        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    /**
     ================================== Update Detail Transaksi by ID ======================================
     */
    @RequestMapping(value = "/detail-cucian/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDetailTransaksi (@PathVariable("id") long id, @RequestBody DAODetailCucian detailCucian) throws SQLException, ClassNotFoundException {

        logger.info("Updating Detail Cucian with {}", id);


//        if (currentProduct == null) {
//            logger.error("Unable to update. Product with id {} not found.", id);
//            return new ResponseEntity<>(new CustomErrorType("Unable to upate. Product with id " + id + " not found."),
//                    HttpStatus.NOT_FOUND);
//        }
       DAODetailCucian detail =  detailCucianRepo.findById(id).orElse(null);
        detail.setStatus(detailCucian.getStatus());
        detail.setPembayaran(detailCucian.getPembayaran());
//
        detailCucianRepo.save(detail);

        return new ResponseEntity<>(detail, HttpStatus.OK);
    }

    @DeleteMapping("delete-transaksi/{id}")
    public ResponseEntity<?> deleteTransaksiById(@PathVariable("id")long id, @RequestBody DAOTransaksi transaksi){

        transaksiService.deleteTransaksi(id);
        return new ResponseEntity<>(transaksiService,HttpStatus.OK);
    }


}







