package com.dss.controller;

import com.dss.entity.AdminEntity;
import com.dss.service.AdminService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private Environment environment;

    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public AdminEntity register(@RequestBody AdminEntity admin) {
        System.out.println("Registration : " + admin);
        AdminEntity savedAdmin = adminService.save(admin);
        return savedAdmin;
    }
    @GetMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody AdminEntity user) {
        String loginMessage = adminService.login(user.getEmailId(),user.getPassword());
        System.out.println(loginMessage);
        return loginMessage;
    }

    @GetMapping("/instance")
    public String getInstancePort(){return environment.getProperty("local.server.port");}

}
