package com.laundry.repository;

import com.laundry.model.DAODetailPembayaran;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PembayaranRepo extends JpaRepository<DAODetailPembayaran, Long> {
}
