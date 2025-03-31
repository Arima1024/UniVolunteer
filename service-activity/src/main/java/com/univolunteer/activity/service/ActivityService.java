package com.univolunteer.activity.service;

import com.univolunteer.common.result.Result;
import com.univolunteer.activity.domain.dto.ActivityCreateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ActivityService {
    Result createActivity(ActivityCreateDTO dto, MultipartFile file);

    Result getActivityList(int page,int size);

    Result updateActivityStatus(Long id, Integer status);

    Result getActivityListByCategory(String category,int page,int size);

    Result getActivityCount();

    Result getNewActivityCount();
}
