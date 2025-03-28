package com.univolunteer.user.service.serviceImpl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.univolunteer.common.result.Result;
import com.univolunteer.user.domain.entity.Organization;
import com.univolunteer.user.domain.entity.Users;
import com.univolunteer.user.mapper.OrganizationMapper;
import com.univolunteer.user.mapper.UserMapper;
import com.univolunteer.user.service.OrganizationService;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl  extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {
    @Override
    public Result getList(String name, int page, int size) {
        Page<Organization> pageInfo = new Page<>(page, size);

        lambdaQuery()
                .like(StringUtils.isNotBlank(name), Organization::getOrganizationName, name)
                .page(pageInfo);


        return Result.ok(pageInfo.getRecords(), pageInfo.getTotal());
    }

    @Override
    public Result getListSchool(String name, int page, int size) {
        Page<Organization> pageInfo = new Page<>(page, size);

        lambdaQuery()
                .eq(Organization::getIsSchool, true) // 添加 is_school = 1 的条件
                .like(StringUtils.isNotBlank(name), Organization::getOrganizationName, name)
                .page(pageInfo);


        return Result.ok(pageInfo.getRecords(), pageInfo.getTotal());
    }

}
