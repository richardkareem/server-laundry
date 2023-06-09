package com.laundry.service;

import com.laundry.config.CustomErrorType;
import com.laundry.model.DAOUser;
import com.laundry.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    public static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Autowired
    UserRepo userRepo;

    public DAOUser findUserById(long id) {

        return  userRepo.findUserById(id);

    }

    public String findRole (long id) {

       Optional<DAOUser> userRole = userRepo.findById(id);
        if (userRole.isPresent()){
            String role = userRole.get().getRole();
            System.out.println("Ini adalah ROLE =" +role);
            System.out.println("Ini adalah ID =" +id);
            return role;
        }else {
            CustomErrorType errorMessage = new CustomErrorType("role tidak ditemukan");
            return String.valueOf(errorMessage);

        }

    }
}
