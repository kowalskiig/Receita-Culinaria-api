package com.project.miinhareceita.user.projection;

public interface UserDetailsProjection {
    String getUsername();
    String getPassword();
    Long getRoleId();
    String getAuthority();
}
