package com.servicerecord.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.servicerecord.domain.dto.RecordDTO;
import com.servicerecord.domain.dto.RecordVO;
import com.servicerecord.domain.entity.VolunteerRecord;
import com.servicerecord.enums.CompletionStatus;
import com.servicerecord.mapper.VolunteerRecordMapper;
import com.servicerecord.service.VolunteerRecordService;
import com.univolunteer.api.client.ActivityClient;
import com.univolunteer.api.client.CommentClient;
import com.univolunteer.api.client.UserClient;
import com.univolunteer.common.context.UserContext;
import com.univolunteer.common.domain.dto.VolunteerDTO;
import com.univolunteer.common.domain.entity.Activity;
import com.univolunteer.common.domain.entity.Users;
import com.univolunteer.common.domain.vo.UserNotificationVO;
import com.univolunteer.common.result.Result;
import com.univolunteer.common.utils.ResultParserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class VolunteerRecordServiceImpl extends ServiceImpl<VolunteerRecordMapper, VolunteerRecord>
        implements VolunteerRecordService {

    private final ActivityClient activityClient;

    private final UserClient userClient;

    private final CommentClient commentClient;

    private final ResultParserUtils resultParserUtils;

    /**
     * 根据时间范围查询志愿记录
     */
    @Override
    public Page<RecordDTO> getRecordsByTimeRange(int page,int size,LocalDateTime startTime, LocalDateTime finishTime) {
        LambdaQueryWrapper<VolunteerRecord> queryWrapper = new LambdaQueryWrapper<>();
        Page<VolunteerRecord> records = new Page<>(page,size);
        queryWrapper.eq(VolunteerRecord::getCompletionStatus, CompletionStatus.COMPLETED);
        //可以自定义时间来进行查询
        if(startTime!=null){
            queryWrapper.ge(VolunteerRecord::getSignInTime, startTime);
        }
        if(finishTime!=null){
            queryWrapper.le(VolunteerRecord::getSignOutTime, finishTime);
        }
        Page<VolunteerRecord> recordPage = baseMapper.selectPage(records, queryWrapper);
        // 将 VolunteerRecord 映射为 RecordDTO
        List<RecordDTO> dtoList = recordPage.getRecords().stream().map(record -> {
            // 从活动服务中获取活动信息，并转换为 Activity 对象
            Activity activity = resultParserUtils.parseData(
                    activityClient.getActivity(record.getActivityId()).getData(), Activity.class
            );
            return new RecordDTO(
                    record.getId(),                // recordId
                    activity.getTitle(),           // activityName
                    activity.getLocation(),        // activityLocation
                    record.getHours(),
                    record.getCompletionStatus(),
                    activity.getStartTime(),
                    activity.getEndTime(),
                    record.getSignInTime(),
                    record.getSignOutTime()
            );
        }).collect(Collectors.toList());

        // 构造新的 RecordDTO 分页对象
        Page<RecordDTO> dtoPage = new Page<>(recordPage.getCurrent(), recordPage.getSize(), recordPage.getTotal());
        dtoPage.setRecords(dtoList);

        return dtoPage;
    }

    /**
     * 按分类和排序方式获取志愿记录
     */
    @Override
    public Page<RecordDTO> getRecordsByClassification(int page, int size, String classification, String sortType) {
        LambdaQueryWrapper<VolunteerRecord> queryWrapper = new LambdaQueryWrapper<>();
        Page<VolunteerRecord> recordsPage = new Page<>(page, size);
        Long userId = UserContext.getUserId();

        // 按分类过滤
        if ("全部".equals(classification)) {
            queryWrapper.eq(VolunteerRecord::getUserId, userId);
        } else if ("待开展".equals(classification)) {
            queryWrapper.eq(VolunteerRecord::getUserId, userId)
                    .eq(VolunteerRecord::getCompletionStatus, CompletionStatus.NOT_STARTED.getValue());
        } else if ("开展中".equals(classification)) {
            queryWrapper.eq(VolunteerRecord::getUserId, userId)
                    .eq(VolunteerRecord::getCompletionStatus, CompletionStatus.IN_PROGRESS.getValue());
        } else if ("已结束".equals(classification)) {
            queryWrapper.eq(VolunteerRecord::getUserId, userId)
                    .eq(VolunteerRecord::getCompletionStatus, CompletionStatus.COMPLETED.getValue());
        } else if ("已放弃".equals(classification)) {
            queryWrapper.eq(VolunteerRecord::getUserId, userId)
                    .eq(VolunteerRecord::getCompletionStatus, CompletionStatus.ABANDONED.getValue());
        }

        // 按排序方式排序
        if ("descTime".equals(sortType)) {
            queryWrapper.orderByDesc(VolunteerRecord::getSignInTime);
        } else {
            queryWrapper.orderByAsc(VolunteerRecord::getSignInTime);
        }

        // 分页查询 VolunteerRecord 数据
        Page<VolunteerRecord> volunteerRecordPage = this.baseMapper.selectPage(recordsPage, queryWrapper);

        // 将 VolunteerRecord 映射为 RecordDTO
        List<RecordDTO> dtoList = volunteerRecordPage.getRecords().stream().map(record -> {
            // 从活动服务中获取活动信息，并转换为 Activity 对象
            Activity activity = resultParserUtils.parseData(
                    activityClient.getActivity(record.getActivityId()).getData(), Activity.class
            );
            return new RecordDTO(
                    record.getId(),                // recordId
                    activity.getTitle(),           // activityName
                    activity.getLocation(),        // activityLocation
                    record.getHours(),
                    record.getCompletionStatus(),
                    activity.getStartTime(),
                    activity.getEndTime(),
                    record.getSignInTime(),
                    record.getSignOutTime()
            );
        }).collect(Collectors.toList());

        // 构造新的 RecordDTO 分页对象
        Page<RecordDTO> dtoPage = new Page<>(volunteerRecordPage.getCurrent(), volunteerRecordPage.getSize(), volunteerRecordPage.getTotal());
        dtoPage.setRecords(dtoList);

        return dtoPage;
    }


    /**
     * 定期更新志愿记录的状态
     * 0 -> 1: 活动开始且签到
     * 1 -> 2: 活动结束且签退
     * 任意 -> 3: 迟到 30 分钟未签到，或结束后 30 分钟未签退
     */
    @Scheduled(fixedRate = 60000)
    @Transactional// 每分钟执行一次
    public void updateRecordStatus() {
        List<VolunteerRecord> records = this.list();
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        for (VolunteerRecord record : records) {
            // 这里可以从 Activity 表获取活动开始和结束时间
            Activity activity = resultParserUtils.parseData(activityClient.getActivity(record.getActivityId()).getData(),Activity.class);
            if (activity == null) {
                continue;
            }
            LocalDateTime activityStartTime = activity.getStartTime(); // 活动计划开始时间
            LocalDateTime activityEndTime = activity.getEndTime(); // 活动计划结束时间

            LocalDateTime signInTime = record.getSignInTime();
            LocalDateTime signOutTime = record.getSignOutTime();
            int status = record.getCompletionStatus();


            if (status == CompletionStatus.NOT_STARTED.getValue()) {
                // **判断是否应该变成 "开展中"**
                if (signInTime != null) {
                    record.setCompletionStatus(CompletionStatus.IN_PROGRESS.getValue());
                }
                // **活动开始 30 分钟后仍未签到，则变成 "已放弃"**
                else if (signInTime == null && now.isAfter(activityStartTime.plusMinutes(30))) {
                    record.setCompletionStatus(CompletionStatus.ABANDONED.getValue());
                }
            }
            else if (status == CompletionStatus.IN_PROGRESS.getValue()) {
                // **已签到 & 活动已结束，则变成 "已完成"**
                if (signOutTime != null) {
                    commentClient.autoGenerateComments(record.getActivityId(),record.getUserId());
                    record.setCompletionStatus(CompletionStatus.COMPLETED.getValue());
                }
                // **活动结束 30 分钟后仍未签退，则变成 "已放弃"**
                else if (signOutTime == null && now.isAfter(activityEndTime.plusMinutes(30))) {
                    record.setCompletionStatus(CompletionStatus.ABANDONED.getValue());
                }
            }

            this.updateById(record);
        }
    }

    @Override
    @Transactional
    public Result addVolunteerRecord(Long activity) {
        Long userId = UserContext.getUserId();
        VolunteerRecord record = new VolunteerRecord();
        record.setUserId(userId);
        record.setActivityId(activity);
        record.setCompletionStatus(CompletionStatus.NOT_STARTED.getValue());
        record.setHours(0.0);

        this.save(record);
        return Result.ok("添加成功");
    }

    @Override
    @Transactional
    public Result signIn(Long recordId) {

        LocalDateTime signInTime=LocalDateTime.now();

        VolunteerRecord record = this.getById(recordId);
        if (record == null) {
            return Result.fail("数据库中暂无此记录");
        }

        // 2. 获取活动信息
        Activity activity = resultParserUtils.parseData(activityClient.getActivity(record.getActivityId()).getData(), Activity.class);

        System.out.println(activity);

        if (activity == null) {
            return Result.fail("活动不存在");
        }
        if (activity.getStartTime() == null) {
            return Result.fail("活动开始时间未定义");
        }

        if (!record.getCompletionStatus().equals(CompletionStatus.NOT_STARTED.getValue())) {
            return Result.fail("状态错误");
        }

        // 4. 避免 NullPointerException
        if (signInTime.isBefore(activity.getStartTime())) {
            return Result.fail("活动尚未开始，不可签到");
        }

        // 5. 更新签到时间
        record.setSignInTime(signInTime);
        this.updateById(record);

        return Result.ok(signInTime);
    }

    @Override
    @Transactional
    public Result signOut(Long recordId) {

        LocalDateTime signOutTime=LocalDateTime.now();

        VolunteerRecord record = this.getById(recordId);

        if (record == null) {
            return Result.fail("数据库中暂无此记录");
        }

        Activity activity = resultParserUtils.parseData(activityClient.getActivity(record.getActivityId()).getData(),Activity.class);

        if (record.getSignInTime() == null) {
            return Result.fail("签到时间不存在，无法签退");
        }
        if(signOutTime.isBefore(activity.getEndTime()))
            return Result.fail("活动尚未结束，不可签退");

        // 计算小时数，确保以 double 类型存储
        long minutes = Duration.between(record.getSignInTime(), signOutTime).toMinutes();
        double hours = minutes / 60.0;

        record.setSignOutTime(signOutTime);
        record.setHours(hours);
        this.updateById(record);

        return Result.ok(signOutTime);
    }

    @Override
    public Double calculateTotalTime(LocalDateTime startTime, LocalDateTime finishTime) {
        Long userId = UserContext.getUserId();
        LambdaQueryWrapper<VolunteerRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VolunteerRecord::getUserId, userId)
                .ge(VolunteerRecord::getSignInTime, startTime)
                .le(VolunteerRecord::getSignOutTime, finishTime);

        List<VolunteerRecord> records = this.list(queryWrapper);

        return records.stream()
                .mapToDouble(record -> record.getHours() != null ? record.getHours() : 0.0)
                .sum();
    }

    @Override
    public VolunteerDTO getVolunteerTime(Long userId) {
        Double totalTime=0.0;
        LambdaQueryWrapper<VolunteerRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VolunteerRecord::getUserId, userId);
        List<VolunteerRecord> records = this.list(queryWrapper);
        totalTime+=records.stream().mapToDouble(record -> record.getHours()).sum();
        VolunteerDTO dto=new VolunteerDTO();
        dto.setCount((long) records.size());
        dto.setTime(totalTime);
        return dto;
    }

    @Override
    public Page<RecordVO> getAllOnlyAdmin(int page, int size, String name, String school, String activityName, String activityLocation, LocalDateTime startTime) {

        LambdaQueryWrapper<VolunteerRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VolunteerRecord::getCompletionStatus, CompletionStatus.COMPLETED.getValue());

        Page<VolunteerRecord> recordsPage = new Page<>(page, size);
        Page<VolunteerRecord> volunteerRecordPage = this.baseMapper.selectPage(recordsPage, queryWrapper);

        // 原始数据转换+过滤
        List<RecordVO> filteredList = volunteerRecordPage.getRecords().stream().map(record -> {
            Activity activity = resultParserUtils.parseData(
                    activityClient.getActivity(record.getActivityId()).getData(), Activity.class
            );
            UserNotificationVO user = resultParserUtils.parseData(
                    userClient.getUserByRecord(record.getUserId()).getData(), UserNotificationVO.class
            );

            return new RecordVO(
                    record.getId(),
                    user.getUsername(),
                    user.getPhone(),
                    user.getOrganizationName(),
                    activity.getTitle(),
                    activity.getStartTime(),
                    activity.getLocation(),
                    record.getHours()
            );
        }).filter(recordVO -> {
            boolean match = true;
            if (name != null && !recordVO.getUserName().contains(name)) match = false;
            if (school != null && !recordVO.getSchool().contains(school)) match = false;
            if (activityName != null && !recordVO.getActivityName().contains(activityName)) match = false;
            if (activityLocation != null && !recordVO.getActivityLocation().contains(activityLocation)) match = false;
            if (startTime != null && recordVO.getStartTime() != null &&
                    recordVO.getStartTime().isBefore(startTime)) match = false;
            return match;
        }).collect(Collectors.toList());

        // 手动分页
        int fromIndex = Math.min((page - 1) * size, filteredList.size());
        int toIndex = Math.min(fromIndex + size, filteredList.size());
        List<RecordVO> pageList = filteredList.subList(fromIndex, toIndex);

        Page<RecordVO> dtoPage = new Page<>(page, size, filteredList.size());
        dtoPage.setRecords(pageList);

        return dtoPage;
    }
}
