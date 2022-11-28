package com.toyproject.lineupproject.auth.jwt.details;

import com.toyproject.lineupproject.auth.jwt.utils.JwtAuthorityUtils;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    private final JwtAuthorityUtils authorityUtils;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(username);
        Admin admin = optionalAdmin.orElseThrow(
                () -> new UsernameNotFoundException(ErrorCode.NOT_FOUND_MEMBER.getMessage()));
        return new CustomUserDetails(admin);
    }

    private final class CustomUserDetails extends Admin implements UserDetails {
        public CustomUserDetails(Admin admin) {
            setEmail(admin.getEmail());
            setNickname(admin.getNickname());
            setPassword(admin.getPassword());
            setPhoneNumber(admin.getPhoneNumber());
            setMemo(admin.getMemo());
            setRoles(admin.getRoles());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

}
