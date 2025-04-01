package com.servicerecord.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.servicerecord.domain.entity.VolunteerRecord;
import com.servicerecord.mapper.VolunteerRecordMapper;
import com.servicerecord.service.VolunteerRecordService;
import org.springframework.stereotype.Service;

@Service
public class VolunteerRecordServiceImpl extends ServiceImpl<VolunteerRecordMapper, VolunteerRecord> implements VolunteerRecordService {
}
