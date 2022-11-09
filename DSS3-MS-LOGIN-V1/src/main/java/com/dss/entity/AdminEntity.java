package com.dss.entity;

import com.dss.model.Admin;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "ADMIN")
@Entity
@Getter
@Setter
public class AdminEntity {
    @Id
    @Column(nullable = false, length = 25)
    private String emailId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;

    public AdminEntity() {

    }

    public AdminEntity(String emailId, String firstName, String lastName, String phoneNumber, String password) {
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }


    public AdminEntity(Admin admin) {
        this.emailId = admin.getEmailId();
        this.firstName = admin.getFirstName();
        this.lastName = admin.getLastName();
        this.phoneNumber = admin.getPhoneNumber();
        this.password = admin.getPassword();
    }

}
