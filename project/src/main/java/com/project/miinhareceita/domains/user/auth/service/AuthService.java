package com.project.miinhareceita.domains.user.auth.service;

import com.project.miinhareceita.domains.user.auth.util.UtilAuth;
import com.project.miinhareceita.domains.user.domain.User;
import com.project.miinhareceita.domains.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UtilAuth utilAuth;

    public User authenticated() {
        try {
            String username = utilAuth.getLoggedToken();
            return repository.findByEmail(username).get();
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("Invalid user");
        }
    }
}
