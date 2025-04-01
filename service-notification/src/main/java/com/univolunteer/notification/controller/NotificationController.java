package com.univolunteer.notification.controller;

import com.univolunteer.api.dto.NotificationDTO;
import com.univolunteer.common.result.Result;
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

    //标记已读
    @PutMapping("/{id}")
    public Result readNotification(@PathVariable Long id){
        return notificationService.readNotification(id);
    }


}
