package com.univolunteer.registration.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.univolunteer.registration.domain.entity.RegistrationHistory;
import com.univolunteer.registration.mapper.RegistrationHistoryMapper;
import com.univolunteer.registration.service.RegistrationHistoryService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationHistoryServiceImpl extends ServiceImpl<RegistrationHistoryMapper, RegistrationHistory> implements RegistrationHistoryService {
}
