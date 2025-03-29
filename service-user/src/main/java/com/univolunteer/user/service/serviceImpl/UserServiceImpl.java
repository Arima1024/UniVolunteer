package com.univolunteer.user.service.serviceImpl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.univolunteer.common.context.UserContext;
import com.univolunteer.common.enums.UserRoleEnum;
import com.univolunteer.common.exception.LoginException;
import com.univolunteer.common.result.Result;
import com.univolunteer.user.domain.dto.LoginUserDto;
import com.univolunteer.user.domain.dto.RegisterUserDto;
import com.univolunteer.user.domain.dto.UpdatePasswordDto;
import com.univolunteer.user.domain.entity.Organization;
import com.univolunteer.user.domain.entity.Users;
import com.univolunteer.user.mapper.OrganizationMapper;
import com.univolunteer.user.mapper.UserMapper;

import com.univolunteer.user.service.UserService;
import com.univolunteer.user.utils.JwtUtils;
import com.univolunteer.user.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, Users> implements UserService {

    private final JwtUtils jwtUtils;

    private final OrganizationMapper organizationMapper;


    @Override
    public Result login(LoginUserDto loginDto) {

        //mybatis-plus 查询用户
        Users user = lambdaQuery()
                .eq(Users::getPhone, loginDto.getPhone())
                .one();
        System.out.println("***************");
        if (user == null) {
            throw new LoginException("账号不存在");
        }
        if (!PasswordUtils.matches(loginDto.getPassword(), user.getPassword())) {
            throw new LoginException("密码错误");
        }
        if (user.getStatus() == 0){
            throw new LoginException("用户已被禁用，请联系管理员");
        }

        user.setLastLoginTime(LocalDateTime.now());
        updateById(user);
        // 获取用户ID和用户名
        Long userId = user.getId();
        String username = user.getUsername();
        String role = user.getRole().name();
        // 生成 JWT
        String jwt =  "Bearer "+ jwtUtils.createToken(userId, username,user.getRole());
        return Result.ok(Map.of("token", jwt,"username",username,"role",role));
    }

    @Override
    public Result register(RegisterUserDto registerDto) {
        // 1. 校验用户名是否已存在
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Users::getPhone, registerDto.getPhone());
        Users exist = baseMapper.selectOne(wrapper);

        if (exist != null) {
            return Result.fail("手机号码["+ registerDto.getPhone() + "]已存在");
        }
        if (!PasswordUtils.isStrongPassword(registerDto.getPassword())) {
            return Result.fail("密码过于简单，至少包含一个小写字母、一个大写字母、一个数字，长度不少于7");
        }
        // 2. 转换角色枚举
        UserRoleEnum roleEnum;
        try {
            roleEnum = registerDto.getRole();
        } catch (Exception e) {
            return Result.fail("无效的角色类型，只能为 VOLUNTEER 或 RECRUITER");
        }

        //2.查出组织的id
        Long organizationId = organizationMapper.selectOne(new QueryWrapper<Organization>().lambda().eq(Organization::getOrganizationName,registerDto.getOrganizationName())).getOrganizationId();
        // 3. 加密密码
        String encodedPassword = PasswordUtils.encode(registerDto.getPassword());

        // 4. 插入新用户
        Users user = new Users();
        user.setUsername(registerDto.getUsername());
        user.setPassword(encodedPassword);
        user.setRole(roleEnum);
        user.setEmail(registerDto.getEmail());
        user.setPhone(registerDto.getPhone());
        user.setCreateTime(LocalDateTime.now());
        user.setOrganizationId(organizationId);
        save(user);

        return Result.ok("注册成功");
    }

    @Override
    public Result resetPassword(UpdatePasswordDto updatePasswordDto) {
        //1.从上下文中获取用户ID
        Long userId = UserContext.getUserId();
        //2.数据库查出该用户
        Users user = getById(userId);
        //3.校验旧密码是否正确
        if (!PasswordUtils.matches(updatePasswordDto.getOldPassword(), user.getPassword())) {
            return Result.fail("旧密码错误");
        }
        //4.检验新旧密码是否相同
        if (updatePasswordDto.getOldPassword().equals(updatePasswordDto.getNewPassword())) {
            return Result.fail("新密码不能与旧密码相同");
        }
        //5.校验新密码是否 sufficiently strong
        if (!PasswordUtils.isStrongPassword(updatePasswordDto.getNewPassword())) {
            return Result.fail("密码过于简单，至少包含一个小写字母、一个大写字母、一个数字，长度不少于7");
        }

        //6.加密密码
        String encodedPassword = PasswordUtils.encode(updatePasswordDto.getNewPassword());
        //7.更新密码
        user.setPassword(encodedPassword);
        //写入数据库
        updateById(user);
        return Result.ok("密码修改成功");
    }

    @Override
    public Result resetPasswordById(Long userId) {
        //根据id查出user
        Users user = getById(userId);
        if (user == null) {
            return Result.fail("用户不存在");
        }
        //todo 个人觉得管理员需要得到信息推送，才知道哪个用户需要重置密码

        //统一重置为Uv12345
        String encodedPassword = PasswordUtils.encode("Uv12345");
        user.setPassword(encodedPassword);
        updateById(user);
        return Result.ok("密码重置成功新密码为Uv12345");
    }

    @Override
    public Result getList(int page, int size) {
        Page<Users> pageInfo = new Page<>(page, size);

        this.lambdaQuery()
                .page(pageInfo); // 正确的分页查询写法

        return Result.ok(pageInfo.getRecords(), pageInfo.getTotal());
    }

}
