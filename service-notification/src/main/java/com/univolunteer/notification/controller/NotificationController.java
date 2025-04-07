package com.univolunteer.notification.controller;

import com.univolunteer.api.dto.NotificationDTO;
import com.univolunteer.common.result.Result;
import com.univolunteer.notification.domain.entity.Announcement;
import com.univolunteer.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    //发送消息
    @PostMapping
    public Result sendNotification(@RequestBody NotificationDTO notificationDTO){
          return notificationService.sendNotification(notificationDTO);
    }


    //获取某个用户全部消息
   @GetMapping("/all")
   public Result getAllNotification(@RequestParam(required = false,defaultValue = "1") Integer page,
                                    @RequestParam(required = false,defaultValue = "10") Integer size){
       return notificationService.getAllNotification(page,size);
   }

    //获取某个用户未读消息
    @GetMapping("/unread")
    public Result getUnreadNotification(@RequestParam(required = false,defaultValue = "1") Integer page,
                                        @RequestParam(required = false,defaultValue = "10") Integer size){
        return notificationService.getUnreadNotification(page,size);
    }

    @GetMapping("/detail/{id}")
    public Result readNotificationDetail(@PathVariable Long id){
        return notificationService.readNotificationDetail(id);
    }




    @GetMapping("/unread/count")
    public Result getUnreadNotificationCount(){
        return notificationService.getUnreadNotificationCount();
    }


    //管理员发送公告
    @PostMapping("/announcement")
    public Result sendAnnouncement(@RequestBody Announcement announcement){
        return notificationService.sendAnnouncement(announcement);
    }

    //获取公告
    @GetMapping("/announcement")
    public Result getAnnouncement(){
        return notificationService.getAnnouncement();
    }

    @GetMapping("/draft")
   public Result getDraft(){
       return notificationService.getDraft();
   }
}
