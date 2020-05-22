package com.iit.aiposizi.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.AbstractMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum AdminRoleEntity {

    ADMIN("ADMIN"),
    ROOT_ADMIN("ROOT_ADMIN");

    private static final Map<String, AdminRoleEntity> MAP = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("ADMIN", ADMIN),
            new AbstractMap.SimpleEntry<>("ROOT_ADMIN", ROOT_ADMIN));

    private final String authority;

    public static AdminRoleEntity fromAuthority(String authority) {
        return MAP.get(authority);
    }

}
