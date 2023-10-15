package com.mprribeiro.authuser.resources;

import com.mprribeiro.authuser.dtos.UserDTO;
import com.mprribeiro.authuser.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AuthenticationResource {

    private final UserService userService;

    @PostMapping("signup")
    public ResponseEntity<Object> registerUser(@RequestBody UserDTO userDTO) {
        final var userModel = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }
}
