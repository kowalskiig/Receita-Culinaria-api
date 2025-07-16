package com.project.miinhareceita.dtos;

import com.project.miinhareceita.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "Campo obrigatório")
    private String firstName;
    private String lastName;

    @Email(message = "Favor entrar um email válido")
    private String email;

    Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(User entity){
        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        email = entity.getEmail();
        entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
    }

    public UserDTO(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
}
