package com.prography.pingpong.system.controller;

import com.prography.pingpong.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    @GetMapping
    public ResponseEntity getServerHealthCondition(){

        return ResponseEntity.ok(200);
    }
}
