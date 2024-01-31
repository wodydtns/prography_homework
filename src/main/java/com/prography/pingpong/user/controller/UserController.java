package com.prography.pingpong.user.controller;

import com.prography.pingpong.common.dto.ApiResponse;
import com.prography.pingpong.user.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String getAllUserList(String size , String page){
        return "ok";
    }

}
