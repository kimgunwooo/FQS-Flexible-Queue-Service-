package com.f4.fqs.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", url = "http://localhost:19092")
public interface AuthServiceClient {

    @GetMapping("/auth/token")
    String createAccessToken(@RequestParam("email") String email);
}
