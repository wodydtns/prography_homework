package com.prography.pingpong.team.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
public class TeamController {

    @PutMapping("{roomId}")
    public String changeTeamByUser(@PathVariable Long roomId){
        return "ok";
    }
}
