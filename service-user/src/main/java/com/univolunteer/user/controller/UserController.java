package com.univolunteer.user.controller;


import com.univolunteer.user.domain.dto.LoginUserDto;
import com.univolunteer.user.domain.dto.RegisterUserDto;
import com.univolunteer.user.service.UserService;
import com.univolunteer.user.utils.JwtUtils;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import com.univolunteer.common.result.Result;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;


    @PostMapping("/login")
    public Result login(@RequestBody LoginUserDto loginDto) {
        return userService.login(loginDto);
    }


    @PostMapping("/register")
    public Result register(@RequestBody RegisterUserDto registerDto) {
        return userService.register(registerDto);
    }

}
