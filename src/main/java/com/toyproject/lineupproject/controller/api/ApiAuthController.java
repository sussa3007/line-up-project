package com.toyproject.lineupproject.controller.api;

import com.toyproject.lineupproject.dto.APIDataResponse;
import com.toyproject.lineupproject.dto.AdminRequest;
import com.toyproject.lineupproject.dto.LoginRequest;
import org.springframework.web.bind.annotation.*;
@Deprecated
//@RequestMapping("/api")
//@RestController
public class ApiAuthController {
    @PostMapping("/sign-up")
    public APIDataResponse<String> signUp(@RequestBody AdminRequest adminRequest) {
        return APIDataResponse.empty();
    }

    @PostMapping("/login")
    public APIDataResponse<String> login(@RequestBody LoginRequest loginRequest) {
        return APIDataResponse.empty();
    }
}