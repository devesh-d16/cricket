package com.devesh.cricket.controller;

import com.devesh.cricket.service.TeamService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis/innings")
public class InningController {

    private final TeamService teamService;

    public InningController(TeamService teamService) {
        this.teamService = teamService;
    }

}
