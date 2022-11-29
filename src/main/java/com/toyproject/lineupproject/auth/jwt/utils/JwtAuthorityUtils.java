package com.toyproject.lineupproject.auth.jwt.utils;

import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthorityUtils {
    @Value("${ADMIN_EMAIL}")
    private String adminMailAddress;

    private final AdminRepository adminRepository;


    public static final List<GrantedAuthority> SUPER_ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_SUPERADMIN","ROLE_ADMIN", "ROLE_USER");

    public static final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");

    public static final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");

    private final List<String> SUPER_ADMIN_ROLES_STRING = List.of("SUPERADMIN","ADMIN", "USER");
    private final List<String> ADMIN_ROLES_STRING = List.of("ADMIN", "USER");
    private final List<String> USER_ROLES_STRING = List.of("USER");

    public List<GrantedAuthority> createAuthorities(List<String> roles) {
        List<GrantedAuthority> result =  roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
                .collect(Collectors.toList());
        return result;
    }

    public List<String> createRoles(String email) {
        Optional<Admin> findUser = adminRepository.findByEmail(email);
        if (findUser.isPresent()) {
            return findUser.get().getRoles();
        } else {
            if (email.equals(adminMailAddress)) {
                return SUPER_ADMIN_ROLES_STRING;
            }
            return USER_ROLES_STRING;
        }
    }
}
