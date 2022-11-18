package com.dss.service;

import com.dss.entity.AdminEntity;
import com.dss.exception.AdminAlreadyExistException;
import com.dss.exception.InvalidInputException;
import com.dss.exception.PhoneNumberAlreadyInUseException;
import com.dss.repository.AdminRepository;
import com.dss.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AdminEntity save(AdminEntity adminEntity) {
        validate(adminEntity);
        try {
            String hashedPassword = hashPassword(adminEntity.getPassword());
            adminEntity.setPassword(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm....");
            throw new RuntimeException(e);
        }
        AdminEntity savedUser = adminRepository.save(adminEntity);
        //• Verify that a new account is created if all the fields that have been entered by the  user are proper.
        System.out.println("Account successfully created!");
        return savedUser;
    }

    @Override
    public AdminEntity findByEmailId(String userName) {
        AdminEntity savedUser = adminRepository.findByEmailId(userName);
        return savedUser;
    }

    @Override
    public AdminEntity findByEmailIdAndPassword(String userName, String password) {
        return adminRepository.findByEmailIdAndPassword(userName, password);
    }

    @Override
    public String login(String userName, String password) {

        if (userName == null || password == null) {
            //• Verify that an error message is displayed if all the details are not filled
            throw new InvalidInputException("Please fill all details");
        }
        String loginMessage;
        try {
            String hashedPassword = hashPassword(password);
            AdminEntity registration = findByEmailIdAndPassword(userName, hashedPassword);
            if (registration != null) {
                loginMessage = "Successfully logged in";
                String token = jwtUtil.generateToken(userName);
                return token;
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm....");
//            rethrowing the exception
            throw new RuntimeException(e);
        }
        loginMessage = "Wrong Username / Password";
        return loginMessage;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        byte[] passwordInBytes = password.getBytes();
        messageDigest.update(passwordInBytes);
        byte[] resultByteArray = messageDigest.digest();
        StringBuilder stringBuilder = new StringBuilder();
        for (byte byteVar : resultByteArray) {
            String formattedString = String.format("%02x", byteVar);
            stringBuilder.append(formattedString);
        }
        String hashedPassword = stringBuilder.toString();
        return hashedPassword;
    }


    public void validate(AdminEntity adminEntity) {
        if (adminEntity.getEmailId() == null || adminEntity.getLastName() == null || adminEntity.getFirstName() == null || adminEntity.getPassword() == null || adminEntity.getPhoneNumber() == null) {
            //• Verify that an error message is displayed if all the details are not filled
            throw new InvalidInputException("Please fill all details");
        }

        if (!Pattern.matches("[a-zA-Z]+", adminEntity.getFirstName()) || !Pattern.matches("[a-zA-Z]+", adminEntity.getLastName())) {
            //• Verify that an error message is displayed if the name contains numbers or special  characters
            throw new InvalidInputException("Name must not contains numbers or special characters");
        }

        if (!Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])((?=.*\\W)|(?=.*_))^[^ ]+$", adminEntity.getPassword())) {
            //• Password should be a combination of at least an uppercase alphabet, lowercase  alphabets, a digit, and a special character.
            throw new InvalidInputException("Password should be a combination of at least an uppercase alphabet, lowercase  alphabets, a digit, and a special character");
        }

        AdminEntity savedAdminEntity = findByEmailId(adminEntity.getEmailId());
        if (savedAdminEntity != null) {
            //• Verify that an error message is displayed if the email id is already in use by some other user
            throw new AdminAlreadyExistException("Admin already exist");
        }

        AdminEntity savedAdminEntity2 = adminRepository.findByPhoneNumber(adminEntity.getPhoneNumber());
        if (savedAdminEntity2 != null) {
            //• Verify that an error message is displayed if the phone number is already in use by  some other user
            throw new PhoneNumberAlreadyInUseException("Phone number is already in use by some other user");
        }

    }
}
