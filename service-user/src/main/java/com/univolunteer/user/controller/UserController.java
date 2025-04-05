package com.univolunteer.user.controller;


import com.univolunteer.common.annotation.AdminOnly;
import com.univolunteer.user.domain.dto.LoginUserDto;
import com.univolunteer.user.domain.dto.RegisterUserDto;
import com.univolunteer.user.domain.dto.UpdatePasswordDto;
import com.univolunteer.user.service.UserService;
import com.univolunteer.user.utils.JwtUtils;


import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import com.univolunteer.common.result.Result;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

   // 登录功能
    @PostMapping("/login")
    public Result login(@RequestBody LoginUserDto loginDto) {

        return userService.login(loginDto);
    }

    // 注册功能
    @PostMapping("/register")
    public Result register(@RequestBody RegisterUserDto registerDto) {
        return userService.register(registerDto);
    }

    // 修改密码功能
    @PutMapping("/resetPassword")
    public Result resetPassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        return userService.resetPassword(updatePasswordDto);
    }

    //重置密码功能
    @AdminOnly
    @PutMapping("/resetPassword/{userId}")
    public Result resetPasswordById(@PathVariable Long userId) {
        return userService.resetPasswordById(userId);
    }



    //查询用户
    @AdminOnly
    @GetMapping
    public Result getUserList(
            @RequestParam(defaultValue = "0") int role,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.getList(role,page, size);
    }

    //获取某位用户的详细信息
    @GetMapping("/{userId}")
    public Result getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }
    //模糊查询用户
    @GetMapping("/search")
    @AdminOnly
    public Result searchUsers(
            @RequestParam(required = false) Integer role,
            @RequestParam(required = false) String organizationName,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String phone,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.searchUsers(role,organizationName, username, phone, page, size);
    }

    @AdminOnly
    @PutMapping("/status/{userId}")
    public Result updateUserStatus(@PathVariable Long userId){
        return userService.updateUserStatus(userId);
    }

    //获取志愿者数量
    @GetMapping("/count")
    public Result getVolunteerCount() {
        return userService.getVolunteerCount();
    }

    //获取日活量
    @GetMapping("/dailyCount")
    public Result getDailyCount() {
        return userService.getDailyCount();
    }
}
