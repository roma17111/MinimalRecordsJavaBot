package com.example.minrecbotjava.controllers.web;

import com.example.minrecbotjava.security.SecurityRole;
import com.example.minrecbotjava.security.UserSecurityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    private final UserSecurityService authService;


    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя")
    public ResponseEntity<?> register(@RequestParam String userName,
                                      @RequestParam String password,
                                      @RequestParam SecurityRole role) {
        if (authService.register(userName,password,role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
