package com.laundry.repository;

import com.laundry.model.DAODetailCucian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailCucianRepo extends JpaRepository<DAODetailCucian, Long> {
}
