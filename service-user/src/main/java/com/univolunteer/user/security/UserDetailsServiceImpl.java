package com.univolunteer.user.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.univolunteer.user.mapper.UserMapper;
import com.univolunteer.user.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        // 1.使用 MyBatis-Plus 查用户
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Users::getPhone, phone);

        Users user = userMapper.selectOne(queryWrapper);


        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        if (user.getStatus() == 0){
            throw new UsernameNotFoundException("用户已被禁用");
        }
        // 2. 更新 last_login_time 字段
        user.setLastLoginTime(LocalDateTime.now());

        // 3. 调用 updateById 更新数据库
        userMapper.updateById(user);
        // 4.封装为 LoginUser 返回
        return new LoginUser(user.getId().longValue(), user.getUsername(), user.getPassword(),user.getRole());
    }
}

