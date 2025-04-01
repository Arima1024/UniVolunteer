package com.univolunteer.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import com.univolunteer.common.domain.vo.ActivityVO;
import com.univolunteer.common.context.UserContext;
import com.univolunteer.common.domain.entity.Activity;
import com.univolunteer.common.domain.entity.ActivityAsset;
import com.univolunteer.common.result.Result;
import com.univolunteer.activity.domain.dto.ActivityCreateDTO;
import com.univolunteer.activity.mapper.ActivityAssetMapper;
import com.univolunteer.activity.mapper.ActivityMapper;
import com.univolunteer.activity.service.ActivityService;
import com.univolunteer.activity.utils.AliOSSUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    private final ActivityMapper activityMapper;
    private final ActivityAssetMapper activityAssetMapper;
    private final AliOSSUtils aliOSSUtils;

    @Override
    public Result createActivity(ActivityCreateDTO dto,MultipartFile file) {
        // 1) 先存活动基础信息
        Activity activity = new Activity();
        activity.setTitle(dto.getTitle());
        activity.setDescription(dto.getDescription());
        activity.setUserId(UserContext.getUserId());
        activity.setCategory(dto.getCategory());
        activity.setLocation(dto.getLocation());
        activity.setStartTime(dto.getStartTime());
        activity.setEndTime(dto.getEndTime());
        activity.setMaxVolunteers(dto.getMaxVolunteers());
        activity.setCreateTime(LocalDateTime.now());
        activity.setSignUpStartTime(dto.getSignUpStartTime());
        activity.setSignUpEndTime(dto.getSignUpEndTime());
        activity.setStatus(0);          // 例如默认审核中
        activity.setCurrentSignUpCount(0L);    // 初始报名人数 0
        System.out.println("activity = " + activity);
        activityMapper.insert(activity);

                try {
                    // 2.1) 上传到 OSS，拿到 fileUrl
                    String fileUrl = aliOSSUtils.upload(file);

                    // 2.2) 写进 activity_assets 表
                    ActivityAsset asset = new ActivityAsset();
                    asset.setActivityId(activity.getId());
                    asset.setFileUrl(fileUrl);
                    asset.setUploadTime(LocalDateTime.now());

                    activityAssetMapper.insert(asset);

                } catch (Exception e) {
                    // 如果这里出错，你可以考虑回滚或者记录日志
                    // 这里简单处理
                    e.printStackTrace();
                }

        // 3) 返回成功信息
        return Result.ok("活动上传成功！");
    }

    @Override
    public Result getActivityList(int page,int size) {
        // 1. 创建分页对象
        Page<Activity> activityPage = new Page<>(page, size);  // pageNo 是页码，pageSize 是每页数量
        // 2. 分页查询 activities 表数据
        Page<Activity> activities = this.page(activityPage, new QueryWrapper<>());
        IPage<ActivityVO> allActivityVO = getAllActivityVO(page, size, activities);
        return Result.ok(allActivityVO.getRecords(), allActivityVO.getTotal());
    }

    @Override
    public Result updateActivityStatus(Long id, Integer status) {
        //使用mybatis-plus进行更新activity里面的状态
        //1.先获取activity
        Activity activity = getById(id);
        //2.更新
        activity.setStatus(status);
        activity.setAuditTime(LocalDateTime.now());
        updateById(activity);
        return Result.ok("活动状态更新成功");
    }

    @Override
    public Result getActivityListByCategory(String category,int pageNo, int pageSize) {
        // 1. 创建分页对象
        Page<Activity> activityPage = new Page<>(pageNo, pageSize);  // pageNo 是页码，pageSize 是每页数量
        // 2. 分页查询 activities 表数据
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        if (category != null && !category.isEmpty()) {
            queryWrapper.eq("category", category);  // 根据 category 字段过滤
        }
        // 3. 分页查询 activities 表数据
        Page<Activity> activities = this.page(activityPage, queryWrapper);
        IPage<ActivityVO> allActivityVO = getAllActivityVO(pageNo, pageSize, activities);
        return Result.ok(allActivityVO.getRecords(), allActivityVO.getTotal());
    }

    @Override
    public Result getActivityCount() {
        //mybatis-plus获取
        Long count = count();
        return Result.ok(Map.of("count", count));
    }

    @Override
    public Result getNewActivityCount() {
        //1.获取当天时间时间为当前日期加上0点
        LocalDateTime today = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(59);
        System.out.println(today);
        //2.与获取的当天时间相比，昨天今天和前天的活动，并且审核通过，即status字段为2
        LocalDateTime dayBeforeYesterday = today.minusDays(2);

        // 使用 QueryWrapper 构建查询条件
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1) // 审核通过的活动 (status = 1)
                .and(wrapper -> wrapper
                        .ge("audit_time", dayBeforeYesterday)  // 前天及之后的活动
                        .lt("audit_time", today)  // 今天之前的活动
                );

        // 查询符合条件的活动列表
        return Result.ok(Map.of("count",this.list(queryWrapper).size()));

    }

    @Override
    public Result getActivity(Long activityId) {
       Activity activity = getById(activityId);
       ActivityVO activityVO = new ActivityVO();
       BeanUtils.copyProperties(activity,activityVO);
       ActivityAsset activityAsset = activityAssetMapper.selectOne(new QueryWrapper<ActivityAsset>().eq("activity_id", activityId));
       activityVO.setImgUrl(activityAsset.getFileUrl());
       return Result.ok(activityVO);
    }

    @Override
    public Result signUp(Long activityId) {
        //对应activity报名人数加1
        Activity activity = getById(activityId);
        activity.setCurrentSignUpCount(activity.getCurrentSignUpCount()+1);
        return Result.ok(updateById(activity));
    }

    @Override
    public Result signDown(Long activityId) {
        Activity activity = getById(activityId);
        //判断当前时间距离活动开展时间是否小于一天，如果小于24小时，则不可以减一
        if (LocalDateTime.now().isBefore(activity.getStartTime().plusHours(24))) {
            return Result.fail("活动开始前24小时，不能取消报名");
        }
        activity.setCurrentSignUpCount(activity.getCurrentSignUpCount()-1);
        return Result.ok(updateById(activity));
    }

    @Override
    public Result check(Long activityId) {
        Activity activity = getById(activityId);
        //判断是否在报名时间范围内
        if (activity.getSignUpStartTime().isAfter(LocalDateTime.now()) || activity.getSignUpEndTime().isBefore(LocalDateTime.now())) {
            return Result.fail("不在报名时间范围内");
        }
        if (activity.getCurrentSignUpCount() >= activity.getMaxVolunteers()) {
            return Result.fail("活动已满");
        }
        return Result.ok();
    }

    @Override
    public Result getActivityListByLocation(String location, int page, int size) {
        //模糊查询让传过来的location和数据库中的location进行模糊匹配
        Page<Activity> activityPage = new Page<>(page, size);
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        if (location != null && !location.isEmpty()) {
            queryWrapper.like("location", location);
        }
        // 3. 分页查询 activities 表数据
        Page<Activity> activities = this.page(activityPage, queryWrapper);
        IPage<ActivityVO> allActivityVO = getAllActivityVO(page, size, activities);
        return Result.ok(allActivityVO.getRecords(), allActivityVO.getTotal());
    }

    @Override
    public Result getActivityListByTime(String time, int page, int size) {
        //将time转换为LocalDateTime
        LocalDateTime day = LocalDateTime.parse(time);
        //选择在当天开始的活动
        LocalDateTime dayStart = day.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime dayEnd = day.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        Page<Activity> activityPage = new Page<>(page, size);
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("start_time", dayStart, dayEnd);
        // 3. 分页查询 activities 表数据
        Page<Activity> activities = this.page(activityPage, queryWrapper);
        IPage<ActivityVO> allActivityVO = getAllActivityVO(page, size, activities);
        return Result.ok(allActivityVO.getRecords(), allActivityVO.getTotal());
    }

    @Override
    public Result getActivityListByStatus(Integer status, int page, int size) {
        Page<Activity> activityPage = new Page<>(page, size);
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        if (status == 3){
            //说明查询已经结束，需要去数据库找到结束时间，与当前时间进行比较，如果大于当前时间，则说明活动已经结束
            queryWrapper.lt("end_time", LocalDateTime.now()).eq("user_id",UserContext.getUserId());
        }else {
            queryWrapper.eq("status", status).eq("user_id",UserContext.getUserId());
        }
        Page<Activity> activities = this.page(activityPage, queryWrapper);
        IPage<ActivityVO> allActivityVO = getAllActivityVO(page, size, activities);
        return Result.ok(allActivityVO.getRecords(), allActivityVO.getTotal());
    }

    @Override
    public Result searchActivity(String keyword, int page, int size) {
        Page<Activity> activityPage = new Page<>(page, size);
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
       queryWrapper.like("title", keyword);
        Page<Activity> activities = this.page(activityPage, queryWrapper);
        IPage<ActivityVO> allActivityVO = getAllActivityVO(page, size, activities);
        return Result.ok(allActivityVO.getRecords(), allActivityVO.getTotal());
    }

    @Override
    public Result getActivityListByTimeAdmin(Integer status, int page, int size) {
        Page<Activity> activityPage = new Page<>(page, size);
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        //拿到当前时间
        LocalDateTime now = LocalDateTime.now();
        if (status == 1) {
            // 未开始的活动
            queryWrapper.gt("start_time", now);
        } else if (status == 2) {
            // 进行中的活动：start_time <= now 且 end_time >= now
            queryWrapper.le("start_time", now).ge("end_time", now);
        } else if (status == 3) {
            // 已结束的活动
            queryWrapper.lt("end_time", now);
        }
        Page<Activity> activities = this.page(activityPage, queryWrapper);
        IPage<ActivityVO> allActivityVO = getAllActivityVO(page, size, activities);
        return Result.ok(allActivityVO.getRecords(), allActivityVO.getTotal());
    }

    @Override
    public Result getActivityListByStatusAdmin(Integer status, int page, int size) {
        //根据状态查询活动
        Page<Activity> activityPage = new Page<>(page, size);
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status);
        Page<Activity> activities = this.page(activityPage, queryWrapper);
        IPage<ActivityVO> allActivityVO = getAllActivityVO(page, size, activities);
        return Result.ok(allActivityVO.getRecords(), allActivityVO.getTotal());
    }

    @Override
    public Result getAllCategory() {
        return Result.ok(activityMapper.selectDistinctCategories());
    }

    private IPage<ActivityVO> getAllActivityVO(int pageNo, int pageSize,Page<Activity> activities) {
        // 3. 获取活动列表和资源列表
        List<Activity> activityList = activities.getRecords();  // 获取分页结果中的记录
        List<ActivityAsset> activityAssets = activityAssetMapper.selectList(new QueryWrapper<>());

        // 4. 使用 Map 来直接根据 activity_id 进行一一对应匹配
        Map<Long, ActivityAsset> assetMap = activityAssets.stream()
                .collect(Collectors.toMap(ActivityAsset::getActivityId, asset -> asset));

        // 5. 封装成 ActivityVO
        List<ActivityVO> activityVOList = activityList.stream().map(activity -> {
            // 通过 activity_id 获取对应的资源
            ActivityAsset asset = assetMap.get(activity.getId());

            // 6. 封装成 ActivityVO
            ActivityVO activityVO = new ActivityVO();
            activityVO.setId(activity.getId());
            activityVO.setTitle(activity.getTitle());
            activityVO.setDescription(activity.getDescription());
            activityVO.setUserId(activity.getUserId());
            activityVO.setCategory(activity.getCategory());
            activityVO.setLocation(activity.getLocation());
            activityVO.setStartTime(activity.getStartTime());
            activityVO.setEndTime(activity.getEndTime());
            activityVO.setMaxVolunteers(activity.getMaxVolunteers());
            activityVO.setStatus(activity.getStatus());
            activityVO.setCreateTime(activity.getCreateTime());
            activityVO.setCurrentSignUpCount(activity.getCurrentSignUpCount());
            activityVO.setSignUpStartTime(activity.getSignUpStartTime());
            activityVO.setSignUpEndTime(activity.getSignUpEndTime());

            // 如果有对应的资源，设置资源URL
            if (asset != null) {
                activityVO.setImgUrl(asset.getFileUrl());  // 一个活动只有一个资源
            }
            return activityVO;
        }).collect(Collectors.toList());

        // 7. 将分页信息和结果封装到 IPage 中
        Page<ActivityVO> activityVOPage = new Page<>();
        activityVOPage.setRecords(activityVOList);  // 设置当前页的活动数据
        activityVOPage.setTotal(activities.getTotal());  // 设置总记录数
        activityVOPage.setPages(activities.getPages());  // 设置总页数
        activityVOPage.setCurrent(activities.getCurrent());  // 设置当前页

        return activityVOPage;
    }

}
