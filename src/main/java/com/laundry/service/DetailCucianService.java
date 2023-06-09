package com.laundry.service;

import com.laundry.model.DAODetailCucian;
import com.laundry.repository.DetailCucianRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetailCucianService {

    @Autowired
    DetailCucianRepo detailCucianRepo;

    // update detail cucian
    public DAODetailCucian findDetailCucianById (long id){
        return  detailCucianRepo.findById(id).orElse(null);
    }
}
