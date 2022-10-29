package com.toyproject.lineupproject.controller.api;

import com.toyproject.lineupproject.dto.APIDataResponse;
import com.toyproject.lineupproject.dto.AdminRequest;
import com.toyproject.lineupproject.dto.LoginRequest;
import org.springframework.web.bind.annotation.*;

//@RequestMapping("/api")
//@RestController
public class APIAuthController {
    @PostMapping("/sign-up")
    public APIDataResponse<String> signUp(@RequestBody AdminRequest adminRequest) {
        return APIDataResponse.empty();
    }

    @PostMapping("/login")
    public APIDataResponse<String> login(@RequestBody LoginRequest loginRequest) {
        return APIDataResponse.empty();
    }
}
