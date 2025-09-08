package com.project.miinhareceita.services;

import com.project.miinhareceita.auth.service.AuthService;
import com.project.miinhareceita.auth.util.UtilAuth;
import com.project.miinhareceita.tests.UserFactory;
import com.project.miinhareceita.domains.user.domain.User;
import com.project.miinhareceita.domains.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UtilAuth utilAuth;

    @Mock
    private UserRepository userRepository;

    private String correctUserNameToken, invalidUserNameToken, existingUserName, nonExistingUsername;
    private User user;



    @BeforeEach
    void setUp(){
        user = UserFactory.createUser();
        existingUserName = "kkowalskigustavo@gmail.com";
        nonExistingUsername =  "maria@gmail.com";


        Mockito.when(userRepository.findByEmail(existingUserName)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByEmail(nonExistingUsername)).thenReturn(Optional.empty());
    }

    @Test
    public void authenticatedShouldReturnUserWhenSucess(){
        Mockito.when(utilAuth.getLoggedToken()).thenReturn(existingUserName);

        User result = authService.authenticated();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getUsername(), existingUserName);
    }
    @Test
    public void authenticatedShouldReturnUserNameNotFoundExceptionWhenUserDoesNotExist(){
        Mockito.when(utilAuth.getLoggedToken()).thenThrow(ClassCastException.class);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            authService.authenticated();
        });
    }
}

