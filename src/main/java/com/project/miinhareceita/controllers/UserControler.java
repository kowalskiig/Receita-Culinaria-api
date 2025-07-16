package com.project.miinhareceita.controllers;

import com.project.miinhareceita.dtos.UserDTO;
import com.project.miinhareceita.dtos.UserInsertDTO;
import com.project.miinhareceita.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserControler {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> insertUser(@Valid @RequestBody UserInsertDTO dto){
        UserDTO receiveUser = userService.insertUser(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(receiveUser.getId()).toUri();

        return ResponseEntity.created(uri).body(receiveUser);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "/me")
    public ResponseEntity<UserDTO> findme() {
        UserDTO dto = userService.findMe();
        return ResponseEntity.ok(dto);
    }
}
