package com.project.miinhareceita.services;

import com.project.miinhareceita.dtos.UserDTO;
import com.project.miinhareceita.dtos.UserInsertDTO;
import com.project.miinhareceita.projections.UserDetailsProjection;
import com.project.miinhareceita.user.repository.RoleRepository;
import com.project.miinhareceita.user.repository.UserRepository;
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
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    private User user;
    private UserDTO dto;

    private String validFirstName;
    private String validLastName;
    private String existingEmail;
    private String nonExistingEmail;
    private String rawPassword;
    private String bCryptPassword;
    private UserInsertDTO userInsertDTO;
    private Role role;



    @BeforeEach
    void setUp(){
        validFirstName = "Gustavo";
        validLastName = "Kowalski";
        existingEmail = "exist@gmail.com";
        nonExistingEmail = "nonexist@gmail.com";
        role = new Role(1L, "ROLE_USER");

        user = new User(1L, validFirstName, validLastName, existingEmail, bCryptPassword);


        rawPassword = "123456";
        bCryptPassword = "$2a$10$.mmz3OqUecF234Bic.FuYO5uZF9eZZGYM7aDkVLpqGVKUqBfhwrAC";
        userInsertDTO = new UserInsertDTO(null, validFirstName, validLastName, existingEmail, rawPassword);


        Mockito.when(passwordEncoder.encode(rawPassword)).thenReturn(bCryptPassword);
        Mockito.when(roleRepository.findByAuthority("ROLE_USER")).thenReturn(role);



        List<UserDetailsProjection> existsUserProjection = new ArrayList<>();
        UserDetailsProjection detailsProjection = new UserDetailsProjection() {
            @Override
            public String getUsername() {
                return existingEmail;
            }

            @Override
            public String getPassword() {
                return "1234";
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

        Mockito.when(repository.save(any(User.class))).thenReturn(user);

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
    public void loadUserByUsernameShouldReturnUsernameNotFoundExceptionWhenEmailDoesNotExist(){

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            UserDetails user = service.loadUserByUsername(nonExistingEmail);
        });
    }

    @Test
    public void insertUserShouldReturnUserDTOwhenDataIsCorrect(){
        UserDTO result = service.insertUser(userInsertDTO);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(user.getId(), result.getId());
        Assertions.assertEquals(userInsertDTO.getFirstName(), result.getFirstName());
        Assertions.assertEquals(userInsertDTO.getLastName(), result.getLastName());
        Assertions.assertEquals(userInsertDTO.getEmail(), result.getEmail());
        Assertions.assertNotNull(result.getRoles());
    }


}
