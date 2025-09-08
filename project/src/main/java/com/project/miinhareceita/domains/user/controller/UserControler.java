package com.project.miinhareceita.domains.user.controller;

import com.project.miinhareceita.domains.user.dto.UserDTO;
import com.project.miinhareceita.domains.user.dto.UserInsertDTO;
import com.project.miinhareceita.domains.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "User", description = "Controller for user")
@RestController
@RequestMapping(value = "/users")
public class UserControler {

    @Autowired
    private UserService userService;

    @Operation(
            description = "Save new User",
            summary = "Save new User",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> insertUser(@Valid @RequestBody UserInsertDTO dto){
        UserDTO receiveUser = userService.insertUser(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(receiveUser.getId()).toUri();

        return ResponseEntity.created(uri).body(receiveUser);
    }

    @Operation(
            description = "Get User",
            summary = "Get User Authenticated",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "/me",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> findme() {
        UserDTO dto = userService.findMe();
        return ResponseEntity.ok(dto);
    }
}
