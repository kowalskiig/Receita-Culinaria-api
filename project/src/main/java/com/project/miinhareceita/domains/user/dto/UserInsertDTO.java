package com.project.miinhareceita.domains.user.dto;

import com.project.miinhareceita.domains.user.validation.UserInsertValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
@UserInsertValid
public class UserInsertDTO extends UserDTO {

    @NotBlank(message = "Campo obrigatório")
    @Size(min = 8, message = "No minimo 8 caracteres")
    private String password;

    public UserInsertDTO() {
        super();
    }

    public UserInsertDTO(Long id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName, email);
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
