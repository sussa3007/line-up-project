package com.toyproject.lineupproject.service;

import com.toyproject.lineupproject.auth.jwt.utils.JwtAuthorityUtils;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtAuthorityUtils authorityUtils;


    public Admin createUser(Admin admin) {
        Optional<Admin> byEmail = adminRepository.findByEmail(admin.getEmail());
        if(byEmail.isPresent()){
            throw new GeneralException(ErrorCode.MEMBER_EXISTS);
        }

        String encodePassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodePassword);

        List<String> roles = authorityUtils.createRoles(admin.getEmail());
        admin.setRoles(roles);


        return adminRepository.save(admin);
    }

    public Admin updateUser(Admin admin) {
        verifiedUser(admin);
        return adminRepository.save(admin);
    }

    @Transactional(readOnly = true)
    public Admin findUser(Admin  admin) {
        return verifiedUser(admin);
    }

    @Transactional(readOnly = true)
    public Admin findUserByEmail(String  email) {
        return verifiedUserByEmail(email);
    }
    private Admin verifiedUser(Admin admin) {
        return adminRepository.findByEmail(admin.getEmail())
                .orElseThrow(
                        () -> new GeneralException(ErrorCode.NOT_FOUND_MEMBER));
    }
    private Admin verifiedUserByEmail(String  email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(
                        () -> new GeneralException(ErrorCode.NOT_FOUND_MEMBER));
    }

}
