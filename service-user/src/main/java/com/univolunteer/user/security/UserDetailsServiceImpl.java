package com.univolunteer.user.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.univolunteer.user.mapper.UserMapper;
import com.univolunteer.user.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 使用 MyBatis-Plus 查用户
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Users::getUsername, username);

        Users user = userMapper.selectOne(queryWrapper);


        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        // 封装为 LoginUser 返回
        return new LoginUser(user.getId().longValue(), user.getUsername(), user.getPassword(),user.getRole());
    }
}

