package com.project.miinhareceita.tests;

import com.project.miinhareceita.user.domain.Role;
import com.project.miinhareceita.user.domain.User;
import com.project.miinhareceita.user.dto.UserInsertDTO;
import com.project.miinhareceita.user.projection.UserDetailsProjection;

public class UserFactory {
    public static final Long DEFAULT_USER_ID = 1L;
    public static final String DEFAULT_FIRST_NAME = "Gustavo";
    public static final String DEFAULT_LAST_NAME = "Kowalski";
    public static final String DEFAULT_EMAIL = "kkowalskigustavo@gmail.com";
    public static final String DEFAULT_RAW_PASSWORD = "password123"; // Adicionado para DTOs de inserção
    public static final String DEFAULT_ENCODED_PASSWORD = "2a$10$.mmz3OqUecF234Bic.FuYO5uZF9eZZGYM7aDkVLpqGVKUqBfhwrAC";


    public static User createUserWithOutRole() {
        User user = new User(DEFAULT_USER_ID, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_ENCODED_PASSWORD);

        return user;
    }

    public static User createUser() {
        Role role = RoleFactory.createRoleUser();
        User user = new User(DEFAULT_USER_ID, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_ENCODED_PASSWORD);

        user.addRole(role);
        return user;
    }

    public static User createUser(Long id, String firstName, String lastName, String email, String password){
        Role role = RoleFactory.createRoleUser();
        User user = new User(id, firstName, lastName, email, password);
        user.addRole(role);

        return user;
    }
    public static UserDetailsProjection createUserDetailsProjection(){
        return new UserDetailsProjection()  {
            @Override
            public String getUsername() {
                return "Gusta";
            }

            @Override
            public String getPassword() {
                return "2a$10$.mmz3OqUecF234Bic.FuYO5uZF9eZZGYM7aDkVLpqGVKUqBfhwrAC";
            }

            @Override
            public Long getRoleId() {
                return 1L;
            }

            @Override
            public String getAuthority() {
                return RoleFactory.createRoleUser().getAuthority();
            }
        };
    }
    public static UserDetailsProjection createUserDetailsProjection(String username, String password, Long roleId, String authority){
        return new UserDetailsProjection()  {
            @Override
            public String getUsername() { return username; }
            @Override
            public String getPassword() { return password; }
            @Override
            public Long getRoleId() { return roleId; }
            @Override
            public String getAuthority() { return authority; }
        };
    }

    public static UserInsertDTO createUserInsertDTO(){
        return new UserInsertDTO(null, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_RAW_PASSWORD);
    }

}

