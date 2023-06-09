package com.laundry.repository;

import com.laundry.model.DAOTransaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransRepo extends JpaRepository<DAOTransaksi, Long> {

}
