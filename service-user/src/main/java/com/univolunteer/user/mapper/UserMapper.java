package com.univolunteer.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.univolunteer.common.domain.entity.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<Users> {
}
