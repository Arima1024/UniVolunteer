package com.univolunteer.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.univolunteer.common.result.Result;
import com.univolunteer.user.domain.entity.Organization;

public interface OrganizationService extends IService<Organization> {
    Result getList(String name, int page, int size);
    Result getListSchool(String name, int page, int size);
}
