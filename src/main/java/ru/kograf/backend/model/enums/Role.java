package ru.kograf.backend.model.enums;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum Role {
    MEMBER("Участник", Set.of(Permission.MEMBER_PERMISSION)),
    LEADER("Руководитель секции", Set.of(Permission.MEMBER_PERMISSION, Permission.LEADER_PERMISSION)),
    ADMIN("Администратор",
            Set.of(Permission.MEMBER_PERMISSION, Permission.LEADER_PERMISSION, Permission.ADMIN_PERMISSION));

    private final String name;
    private final Set<Permission> permissions;

    Role(String name, Set<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
