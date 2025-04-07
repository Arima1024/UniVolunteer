package com.univolunteer.user.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.univolunteer.common.annotation.AdminOnly;
import com.univolunteer.common.result.Result;
import com.univolunteer.user.domain.entity.Organization;
import com.univolunteer.user.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    //添加组织
    @AdminOnly
    @PostMapping
    public Result addOrganization(@RequestBody Organization organization) {
        return Result.ok(organizationService.save(organization));
    }

    //查询学校列表
    @GetMapping("/school")
    public Result getOrganizationListSchool(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return organizationService.getListSchool(name, page, size);
    }

    //查询组织列表
    @GetMapping
    public Result getOrganizationList(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return organizationService.getList(name, page, size);
    }

    @GetMapping("/count")
    public Result getOrganizationCount() {
        return Result.ok(organizationService.count());
    }


}
