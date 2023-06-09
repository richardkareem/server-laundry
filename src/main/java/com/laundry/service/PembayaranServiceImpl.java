package com.laundry.service;

import com.laundry.model.DAODetailPembayaran;
import com.laundry.repository.PembayaranRepo;
import com.laundry.serviceInterface.PembayaranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PembayaranServiceImpl implements PembayaranService {
    @Autowired
    PembayaranRepo pembayaranRepo;

    @Override
    public DAODetailPembayaran savePembayaran(DAODetailPembayaran daoDetailPembayaran) {

       
        return null;
    }
}
