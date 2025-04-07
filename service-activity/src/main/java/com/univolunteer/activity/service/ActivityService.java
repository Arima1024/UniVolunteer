package com.univolunteer.activity.service;

import com.univolunteer.common.result.Result;
import com.univolunteer.activity.domain.dto.ActivityCreateDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ActivityService {
    Result createActivity(ActivityCreateDTO dto, MultipartFile file);

    Result getActivityList(int page,int size);

    Result updateActivityStatus(Long id, Integer status, String reason);

    Result getActivityListByCategory(String category,int page,int size);

    Result getActivityCount();

    Result getNewActivityCount();

    Result getActivity(Long activityId);

    Result signUp(Long activityId);

    Result signDown(Long activityId);

    Result check(Long activityId);

    Result getActivityListByLocation(String location, int page, int size);

    Result getActivityListByTime(String time, int page, int size);

    Result getActivityListByStatus(Integer status, int page, int size);

    Result searchActivity(String keyword, int page, int size);

    Result getActivityListByTimeAdmin(Integer status, int page, int size);

    Result getActivityListByStatusAdmin(Integer status, int page, int size);

    Result getAllCategory();

    Result getAllActivityByVolunteer(int page, int size);

    Result getActivityListByAllStatus(String keyword,Integer status, Integer timeStatus, String category, Integer sortType,int page, int size);

    Result getActivityCountByUserId(Long userId);

    Result getActivityListByVolunteerStatus(String category, String time, String location, int page, int size);
}
