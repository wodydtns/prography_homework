package com.prography.pingpong.room.controller;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/room")
@RestController
public class RoomController {

    @PostMapping
    public String createRoom(){
        return "ok";
    }

    @GetMapping
    public String getAllRoom(String size, String page){
        return "ok";
    }

    @GetMapping("/{roomId}")
    public String getRoomDetail(@PathVariable Long roomId){
        return "ok";
    }

    @PostMapping("/attention/{roomId}")
    public String joinTheRoom(@PathVariable Long roomId){
        return "ok";
    }

    @PostMapping("/out/{roomId}")
    public String exitTheRoom(@PathVariable Long roomId){
        return "ok";
    }

    @PutMapping("/start/{roomId}")
    public String startPingpongGame(@PathVariable Long roomId){
        return "ok";
    }
}
