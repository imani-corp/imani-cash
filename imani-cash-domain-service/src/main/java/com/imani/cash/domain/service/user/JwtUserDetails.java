package com.imani.cash.domain.service.user;

import com.imani.cash.domain.user.UserRecord;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUserDetails implements UserDetails {


    private UserRecord userRecord;

    public JwtUserDetails(UserRecord userRecord) {
        this.userRecord = userRecord;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(userRecord.getUserRecordTypeE().toString());
    }

    @Override
    public String getPassword() {
        return userRecord.getPassword();
    }

    @Override
    public String getUsername() {
        return userRecord.getEmbeddedContactInfo().getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !userRecord.isAccountLocked();
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
