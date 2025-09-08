package com.project.miinhareceita.domains.user.dto;

import com.project.miinhareceita.domains.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoleDTO {
    private Long id;
    private String authority;

    public RoleDTO(Role entity){
        id = entity.getId();
        authority = entity.getAuthority();
    }
}
