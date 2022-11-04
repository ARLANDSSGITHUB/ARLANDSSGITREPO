package com.dss.service;

import com.dss.entity.AdminEntity;

public interface AdminService {
    AdminEntity save(AdminEntity adminEntity);

    AdminEntity findByEmailId(String userName);

    AdminEntity findByEmailIdAndPassword(String userName, String password);

    String login(String userName, String password);

}
