package com.toyproject.lineupproject.service;

import com.toyproject.lineupproject.auth.jwt.utils.JwtAuthorityUtils;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.dto.AdminResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtAuthorityUtils authorityUtils;


    public Admin createUser(Admin admin) {
        if (admin.getStatus() == null) {
            admin.setStatus(Admin.Status.ACTIVE_USER);
            admin.setLoginBase(Admin.LoginBase.BASIC_LOGIN);
        }

        verifyUserInfo(admin);

        String encodePassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodePassword);

        List<String> roles = authorityUtils.createRoles(admin.getEmail());
        admin.setRoles(roles);


        return adminRepository.save(admin);
    }


    public void updateUser(Admin admin) {
        //todo update시 비밀번호 인코딩 안됨 에러
        String password = admin.getPassword();
        if (password != null) {
            String encodePassword = passwordEncoder.encode(password);
            admin.setPassword(encodePassword);
        }

        Admin findAdmin = verifiedUser(admin);
        findAdmin.updateEntity(admin);
    }

    @Transactional(readOnly = true)
    public Admin findUser(Admin  admin) {
        return verifiedUser(admin);
    }

    @Transactional(readOnly = true)
    public Admin findUserById(Long  userId) {
        return adminRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND_MEMBER));
    }


    @Transactional(readOnly = true)
    public Admin findUserByEmail(String  email) {
        return verifiedUserByEmail(email);
    }

    @Transactional(readOnly = true)
    public Page<AdminResponse> findAllUser(Pageable pageable) {
        return adminRepository.findAll(pageable).map(AdminResponse::of);
    }


    // 검증 기능 로직

    private void verifyUserInfo(Admin admin) {
        if(adminRepository.findByEmail(admin.getEmail()).isPresent()){
            throw new GeneralException(ErrorCode.MEMBER_EXISTS);
        }
        if(adminRepository.findByNickname(admin.getNickname()).isPresent()){
            throw new GeneralException(ErrorCode.NICKNAME_EXISTS);
        }
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

    public Optional<Admin> findUserByEmailToOptional(String email) {
        return adminRepository.findByEmail(email);
    }

}
