package com.project.miinhareceita.dtos;

import com.project.miinhareceita.user.domain.User;
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
