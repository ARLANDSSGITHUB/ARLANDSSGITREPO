package com.dss.repository;

import com.dss.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, String> {
    AdminEntity findByEmailId(String userName);

    AdminEntity findByEmailIdAndPassword(String email, String password);

    AdminEntity findByPhoneNumber(String phoneNumber);
}
