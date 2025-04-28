package com.d3n15.user_info_service.controller;

import com.d3n15.user_info_service.dto.UserInfoDTO;
import com.d3n15.user_info_service.service.UserInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/{userId}")
    public UserInfoDTO getUserInfo(@PathVariable String userId) {
        return userInfoService.getUserInfoByUserId(userId);
    }
}