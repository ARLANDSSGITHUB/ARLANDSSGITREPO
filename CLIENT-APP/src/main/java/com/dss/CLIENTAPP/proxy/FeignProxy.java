package com.dss.CLIENTAPP.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="login-service")
public interface FeignProxy {
    @GetMapping("/api/admin/instance")
    public String getServiceInstance();

}
