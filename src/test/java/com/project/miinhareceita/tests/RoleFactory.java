package com.project.miinhareceita.tests;

import com.project.miinhareceita.user.domain.Role;

public class RoleFactory {
    public static final Long DEFAULT_ROLE_ID = 1L;
    public static final Long DEFAULT_ROLE_ADMIN_ID = 1L;
    public static final String DEFAULT_ROLE_USER_AUTHORITY = "ROLE_USER";
    public static final String DEFAULT_ROLE_ADMIN_AUTHORITY = "ROLE_ADMIN";

    public static Role createRoleUser(){
        return new Role(DEFAULT_ROLE_ID, DEFAULT_ROLE_USER_AUTHORITY);
    }

    public static Role createRoleAdmin(){
        return new Role(DEFAULT_ROLE_ADMIN_ID, DEFAULT_ROLE_ADMIN_AUTHORITY);
    }
    public static Role createRole(Long id, String roleName){
        return new Role(id, roleName);
    }

}
