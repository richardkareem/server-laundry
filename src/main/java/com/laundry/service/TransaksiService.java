package com.laundry.service;

import com.laundry.model.DAOTransaksi;
import com.laundry.model.DAOUser;
import com.laundry.repository.TransRepo;
import com.laundry.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

    @Service
    public class TransaksiService {
    @Autowired
    private TransRepo transRepo;

    // menyimpan transaksi & detail transnya

    @Autowired
    private UserRepo userRepo;

    //cek id nya si user
    public DAOUser findById (long idUser){ //method ambil id user
       DAOUser user = userRepo.findUserById(idUser);

        if (user == null){
            throw  new IllegalArgumentException(
                    "User dengan nama "+ idUser + " Belum Terdaftar, Register terlebih dahulu");
        }
        return user;
    }


    public DAOTransaksi save(DAOTransaksi daoTransaksi){
        return transRepo.save(daoTransaksi);
    }

    public void deleteTransaksi(long id){

        transRepo.deleteById(id);
    }


}
