package com.univolunteer.user.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.univolunteer.common.enums.UserRoleEnum;
import com.univolunteer.common.result.Result;
import com.univolunteer.user.domain.dto.LoginUserDto;
import com.univolunteer.user.domain.dto.RegisterUserDto;
import com.univolunteer.user.domain.entity.Users;
import com.univolunteer.user.mapper.UserMapper;
import com.univolunteer.user.security.LoginUser;
import com.univolunteer.user.service.UserService;
import com.univolunteer.user.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, Users> implements UserService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Result login(LoginUserDto loginDto) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

       // Security 会自动调用 UserDetailsServiceImpl + LoginUser
        Authentication authentication = authenticationManager.authenticate(token);
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 获取用户ID和用户名
        Long userId = loginUser.getId();
        String username = loginUser.getUsername();
        String role = loginUser.getRole().name();
        // 生成 JWT
        String jwt =  "Bearer "+ jwtUtils.createToken(userId, username,loginUser.getRole());
        return Result.ok(Map.of("token", jwt,"username",username,"role",role));

    }

    @Override
    public Result register(RegisterUserDto registerDto) {
        // 1. 校验用户名是否已存在
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Users::getUsername, registerDto.getUsername());
        Users exist = baseMapper.selectOne(wrapper);

        if (exist != null) {
            return Result.fail("用户名已存在");
        }

        // 2. 转换角色枚举
        UserRoleEnum roleEnum;
        try {
            roleEnum = registerDto.getRole();
        } catch (Exception e) {
            return Result.fail("无效的角色类型，只能为 VOLUNTEER 或 RECRUITER");
        }

        // 3. 加密密码
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());

        // 4. 插入新用户
        Users user = new Users();
        user.setUsername(registerDto.getUsername());
        user.setPassword(encodedPassword);
        user.setRole(roleEnum);
        user.setEmail(registerDto.getEmail());
        user.setPhone(registerDto.getPhone());
        user.setCreateTime(LocalDateTime.now());
        save(user);

        return Result.ok("注册成功");
    }
}
