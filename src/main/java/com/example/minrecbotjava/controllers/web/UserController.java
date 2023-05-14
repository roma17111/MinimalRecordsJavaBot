package com.example.minrecbotjava.controllers.web;

import com.example.minrecbotjava.entity.UserInfo;
import com.example.minrecbotjava.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/admin")
    @Operation(summary = "Дать роль пользователю")
    public UserInfo setRoleUser(@RequestParam boolean isAdmin,
                                @RequestParam long id) {
        return userService.setRoleToUserById(isAdmin, id);
    }

    @PatchMapping("/")
    @Operation(summary = "Посмотреть список всех пользователей")
    public List<UserInfo> findAllUsers() {
        return userService.findAll();
    }

}
