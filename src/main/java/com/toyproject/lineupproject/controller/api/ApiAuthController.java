package com.toyproject.lineupproject.controller.api;

import com.toyproject.lineupproject.dto.ApiDataResponse;
import com.toyproject.lineupproject.dto.AdminRequest;
import com.toyproject.lineupproject.dto.LoginRequest;
import org.springframework.web.bind.annotation.*;
@Deprecated
//@RequestMapping("/api")
//@RestController
public class ApiAuthController {
    @PostMapping("/sign-up")
    public ApiDataResponse<String> signUp(@RequestBody AdminRequest adminRequest) {
        return ApiDataResponse.empty();
    }

    @PostMapping("/login")
    public ApiDataResponse<String> login(@RequestBody LoginRequest loginRequest) {
        return ApiDataResponse.empty();
    }
}
