package com.adpanel.adpanel.model.enums;

public enum Permission {
    DEVELOPERS_READ("developers:read"),
    DEVELOPERS_CREATE_USER("developers:create:user"),
    DEVELOPER_GENERATE_LINK("developers:generate:link");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
