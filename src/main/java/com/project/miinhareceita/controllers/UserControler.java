package com.project.miinhareceita.controllers;

import com.project.miinhareceita.dtos.UserDTO;
import com.project.miinhareceita.dtos.UserInsertDTO;
import com.project.miinhareceita.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserControler {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> insertUser(@RequestBody UserInsertDTO dto){
        UserDTO receiveUser = userService.insertUser(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(receiveUser.getId()).toUri();

        return ResponseEntity.created(uri).body(receiveUser);
    }
}
