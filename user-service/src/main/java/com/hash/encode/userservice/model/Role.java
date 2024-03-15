package com.hash.encode.userservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hash.encode.userservice.model.Permission.*;

@RequiredArgsConstructor
public enum Role {

    USER(
            Set.of(USER_READ, USER_UPDATE, USER_DELETE, ADMIN_READ)
    ),
    ADMIN(
            Set.of(ADMIN_READ, ADMIN_UPDATE, ADMIN_DELETE)
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
