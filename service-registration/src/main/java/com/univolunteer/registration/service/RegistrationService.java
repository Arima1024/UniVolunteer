package com.univolunteer.registration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.univolunteer.common.result.Result;
import com.univolunteer.registration.domain.entity.Registration;

public interface RegistrationService extends IService<Registration> {
    Result register(Long activityId);

    Result check(Long registrationId, Integer status,String reason);

    Result cancel(Long registrationId,String reason);

    Result getRegistrationListByStatus(Integer status,int page, int size);
}
