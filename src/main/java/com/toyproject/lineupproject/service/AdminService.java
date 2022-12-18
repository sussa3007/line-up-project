package com.toyproject.lineupproject.service;

import com.querydsl.core.types.Predicate;
import com.toyproject.lineupproject.auth.jwt.utils.JwtAuthorityUtils;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.dto.AdminResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
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
        Admin findUser = verifiedUserByEmail(admin.getEmail());
        String password = admin.getPassword();
        if (!password.equals("")) {
            String encodePassword = passwordEncoder.encode(password);
            admin.setPassword(encodePassword);
        } else {
            admin.setPassword(findUser.getPassword());
        }

        updateEntity(findUser,admin);
    }

    @Transactional(readOnly = true)
    public Admin findUser(Admin  admin) {
        return verifiedGetUser(admin);
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
    public Page<AdminResponse> finderUsers(
            String statusKey,
            Predicate predicate ,
            Pageable pageable
    ) {
        Page<AdminResponse> allUser = null;
        if (statusKey != null) {
            if(statusKey.equals("active")){
                allUser = findAllUserByStatus(
                        Admin.Status.valueOf((statusKey + "_user").toUpperCase()),
                        pageable);
            } else if(statusKey.equals("inActive")){
                allUser = findAllUserByStatus(
                        Admin.Status.valueOf((statusKey + "_user").toUpperCase()),
                        pageable);
            } else {
                allUser = findAllUserByRoles(
                        statusKey.toUpperCase(),
                        pageable
                );
            }
        } else {
            allUser = findAllUser(predicate,pageable);
        }

        return allUser;
    }

    private Page<AdminResponse> findAllUser(Predicate predicate, Pageable pageable) {
        Page<Admin> all = adminRepository.findAll(predicate, pageable);
        List<AdminResponse> list = all.getContent()
                .stream().map(AdminResponse::of).toList();
        return new PageImpl<>(list, all.getPageable(), all.getTotalElements());
    }
    private Page<AdminResponse> findAllUserByStatus(
            Admin.Status status,
            Pageable pageable) {

        Page<Admin> all = adminRepository.findAllByStatus(status,pageable);
        List<AdminResponse> list = all.getContent()
                .stream().map(AdminResponse::of).toList();
        return new PageImpl<>(list, all.getPageable(), all.getTotalElements());
    }
    private Page<AdminResponse> findAllUserByRoles(
            String roles,
            Pageable pageable) {

        Page<Admin> all = adminRepository.findByRolesIn(List.of(roles),pageable);
        List<AdminResponse> list = all.getContent()
                .stream().map(AdminResponse::of).toList();
        return new PageImpl<>(list, all.getPageable(), all.getTotalElements());
    }


    // 검증 기능 로직
    public boolean verifyUserByEmail(String email) {
        return adminRepository.findByEmail(email).isEmpty();
    }

    private void verifyUserInfo(Admin admin) {
        if(adminRepository.findByEmail(admin.getEmail()).isPresent()){
            throw new GeneralException(ErrorCode.MEMBER_EXISTS);
        }
        if(adminRepository.findByNickname(admin.getNickname()).isPresent()){
            throw new GeneralException(ErrorCode.NICKNAME_EXISTS);
        }
    }
    public AdminResponse verifiedUser(String email) {
        Admin admin1 = adminRepository.findByEmail(email)
                .orElseThrow(
                        () -> new GeneralException(ErrorCode.NOT_FOUND_MEMBER));
        return AdminResponse.of(admin1);
    }
    private Admin verifiedGetUser(Admin admin) {
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


    public void updateEntity(Admin findUser, Admin requestUser) {
        String roles = findUser.getRoles().toString();
        List<String> newRoles = new ArrayList<>();
        // TODO 영속성 user의 Roles를 설정해줘도 삭제 쿼리가 날라간다 왜그럴까?
        // [SUPERADMIN, ADMIN, USER]
        // [ADMIN, USER]
        // [USER]
        if (requestUser.getRoles().isEmpty()) {
            if (roles.equals("[USER]")) {
                newRoles = JwtAuthorityUtils.USER_ROLES_STRINGS;
            } else if (roles.equals("[ADMIN, USER]")) {
                newRoles = JwtAuthorityUtils.ADMIN_ROLES_STRINGS;
            } else if (roles.equals("[SUPERADMIN, ADMIN, USER]")) {
                newRoles = JwtAuthorityUtils.SUPER_ADMIN_ROLES_STRINGS;
            } else {
                throw new GeneralException(ErrorCode.BAD_REQUEST);
            }
            requestUser.setRoles(newRoles);
        }
        findUser.setRoles(Optional.ofNullable(requestUser.getRoles()).orElse(newRoles));
        findUser.setEmail(Optional.ofNullable(requestUser.getEmail()).orElse(findUser.getEmail()));
        findUser.setNickname(Optional.ofNullable(requestUser.getNickname()).orElse(findUser.getNickname()));
        findUser.setPassword(Optional.ofNullable(requestUser.getPassword()).orElse(findUser.getPassword()));
        findUser.setPhoneNumber(Optional.ofNullable(requestUser.getPhoneNumber()).orElse(findUser.getPhoneNumber()));
        findUser.setMemo(Optional.ofNullable(requestUser.getMemo()).orElse(findUser.getMemo()));
        findUser.setStatus(Optional.ofNullable(requestUser.getStatus()).orElse(findUser.getStatus()));
        findUser.setLoginBase(Optional.ofNullable(requestUser.getLoginBase()).orElse(findUser.getLoginBase()));
    }

}
