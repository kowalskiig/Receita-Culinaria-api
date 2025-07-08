package com.project.miinhareceita.services;

import com.project.miinhareceita.projections.UserDetailsProjection;
import com.project.miinhareceita.repositories.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private String existingEmail;
    private String nonExistingEmail;
    private String password;



    @BeforeEach
    void setUp(){
        existingEmail = "exist@gmail.com";
        nonExistingEmail = "nonexist@gmail.com";
        password = "123";

        List<UserDetailsProjection>  existsUserProjection = new ArrayList<>();
        UserDetailsProjection detailsProjection = new UserDetailsProjection() {
            @Override
            public String getUsername() {
                return existingEmail;
            }

            @Override
            public String getPassword() {
                return password;
            }

            @Override
            public Long getRoleId() {
                return 0L;
            }

            @Override
            public String getAuthority() {
                return "";
            }
        };
        existsUserProjection.add(detailsProjection);
        
        Mockito.when(repository.searchUserAndRolesByEmail(existingEmail)).thenReturn(existsUserProjection);

        Mockito.when(repository.searchUserAndRolesByEmail(nonExistingEmail)).thenThrow(UsernameNotFoundException.class);

    }

    @Test
    public void loadUserByUsernameShouldReturnUserWhenEmailExists(){

        UserDetails user = service.loadUserByUsername(existingEmail);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getUsername(), existingEmail);
        Assertions.assertNotNull(user.getPassword());


    }

    @Test
    public void loadUserByUsernameShouldReturnUsernameNotFoundExceptionWhenIdDoesNotExist(){

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            UserDetails user = service.loadUserByUsername(nonExistingEmail);
        });
    }
}
