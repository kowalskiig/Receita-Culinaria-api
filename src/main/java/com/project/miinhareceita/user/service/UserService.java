package com.project.miinhareceita.user.service;

import com.project.miinhareceita.auth.service.AuthService;
import com.project.miinhareceita.user.domain.Role;
import com.project.miinhareceita.user.domain.User;
import com.project.miinhareceita.user.dto.UserDTO;
import com.project.miinhareceita.user.dto.UserInsertDTO;
import com.project.miinhareceita.user.projection.UserDetailsProjection;
import com.project.miinhareceita.user.repository.RoleRepository;
import com.project.miinhareceita.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("Email not found");
        }

        User user = new User();
        user.setEmail(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());
        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }

        return user;
    }

    @Transactional
    public UserDTO insertUser(UserInsertDTO dto) {
        User recieveUser = copyDtoToEntity(dto);
        recieveUser.setPassword(passwordEncoder.encode(dto.getPassword()));

        recieveUser.getRoles().clear();
        recieveUser.getRoles().add(createRoleByRoleName("ROLE_USER"));

        recieveUser = repository.save(recieveUser);

        return new UserDTO(recieveUser);
    }

    @Transactional(readOnly = true)
    public UserDTO findMe() {
        User obj = authService.authenticated();
        return new UserDTO(obj);
    }



    private User copyDtoToEntity(UserDTO dto){
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        return user;
    }

    private Role createRoleByRoleName(String roleName){
        Role role = roleRepository.findByAuthority(roleName);
        return role;
    }
//

}
