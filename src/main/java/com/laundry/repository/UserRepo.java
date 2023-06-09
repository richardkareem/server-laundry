package com.laundry.repository;

import com.laundry.model.DAOUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository <DAOUser, Long> {
    public DAOUser findUserById(long id);

    public DAOUser findUserByRole(String role);

}
