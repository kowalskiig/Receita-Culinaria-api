package com.project.miinhareceita.domains.user.dto;

import com.project.miinhareceita.domains.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserMinDTO {
    private Long id;
    private String firstName;
    private String lastName;


    public UserMinDTO(User user){
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
    }


}
