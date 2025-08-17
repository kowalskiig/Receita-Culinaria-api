package com.project.miinhareceita.services;



import com.project.miinhareceita.auth.service.AuthService;
import com.project.miinhareceita.tests.RoleFactory;
import com.project.miinhareceita.tests.UserFactory;
import com.project.miinhareceita.user.domain.Role;

import com.project.miinhareceita.user.domain.User;

import com.project.miinhareceita.user.dto.UserDTO;

import com.project.miinhareceita.user.dto.UserInsertDTO;

import com.project.miinhareceita.user.projection.UserDetailsProjection;

import com.project.miinhareceita.user.repository.RoleRepository;

import com.project.miinhareceita.user.repository.UserRepository;

import com.project.miinhareceita.user.service.UserService;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.Mockito;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.test.context.junit.jupiter.SpringExtension;



import java.util.ArrayList;

import java.util.List;



import static org.mockito.ArgumentMatchers.any;



@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AuthService authService;

    private User user;
    private String rawPassword;
    private String encodedPassword;
    private Role userRole;



    private UserDetailsProjection userDetailsProjection;
    private List<UserDetailsProjection> userDetailsProjectionsList;
    private List<UserDetailsProjection> nonUserDetailsProjectionsList;

    private String existingEmail, nonExistingEmail;






    @BeforeEach

    void setUp() {
        user = UserFactory.createUserWithOutRole();
        userRole = RoleFactory.createRoleUser();


    userDetailsProjection = UserFactory.createUserDetailsProjection();
    userDetailsProjectionsList = new ArrayList<>();
    userDetailsProjectionsList.add(userDetailsProjection);
    nonUserDetailsProjectionsList = new ArrayList<>();

    existingEmail = "existingemail@gmail.com";
    nonExistingEmail = "nonexistingemail@gmail.com";

    rawPassword = "123456";
    encodedPassword = "\"2a$10$.mmz3OqUecF234Bic.FuYO5uZF9eZZGYM7aDkVLpqGVKUqBfhwrAC";


    Mockito.when(userRepository.searchUserAndRolesByEmail(existingEmail)).thenReturn(userDetailsProjectionsList);
    Mockito.when(userRepository.searchUserAndRolesByEmail(nonExistingEmail)).thenReturn(nonUserDetailsProjectionsList);

    Mockito.when(userRepository.save(any())).thenReturn(user);
    Mockito.when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
    Mockito.when(roleRepository.findByAuthority("ROLE_USER")).thenReturn(userRole);
    Mockito.when(authService.authenticated()).thenReturn(user);
    }

    @Test

    public void loadUserByUsernameShouldReturnUserWhenEmailExists(){

        UserDetails result = service.loadUserByUsername(existingEmail);


        Assertions.assertNotNull(result);
        Assertions.assertEquals(userDetailsProjectionsList.getFirst().getUsername(), result.getUsername());
        Assertions.assertEquals(userDetailsProjectionsList.getFirst().getPassword(), result.getPassword());
        Assertions.assertTrue(result.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));

    }



    @Test

    public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenEmailDoesNotExist(){

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {

            service.loadUserByUsername(nonExistingEmail);

        });

    }


    @Test
    public void insertUserShouldReturnUserDTOWhenInsertSucess(){
        UserInsertDTO userInsertDTO = UserFactory.createUserInsertDTO();
        UserDTO result = service.insertUser(userInsertDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), user.getId());
        Assertions.assertEquals(result.getFirstName(), user.getFirstName());
        Assertions.assertEquals(result.getLastName(), user.getLastName());
        Assertions.assertEquals(result.getEmail(), user.getEmail());
        Assertions.assertNotNull(result.getRoles());

        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(userInsertDTO.getPassword());

        Mockito.verify(roleRepository, Mockito.times(1)).findByAuthority("ROLE_USER");

        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    public void findMeShouldReturnUserDTOWhenSucess(){
        UserDTO result = service.findMe();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getFirstName(), user.getFirstName());
        Assertions.assertEquals(result.getLastName(), user.getLastName());
        Assertions.assertEquals(result.getEmail(), user.getEmail());
        Assertions.assertNotNull(result.getRoles());

        Mockito.verify(authService, Mockito.times(1)).authenticated();
    }



}