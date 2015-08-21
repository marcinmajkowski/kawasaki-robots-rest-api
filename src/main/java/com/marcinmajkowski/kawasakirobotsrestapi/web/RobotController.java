package com.marcinmajkowski.kawasakirobotsrestapi.web;

import com.marcinmajkowski.kawasakirobotsrestapi.domain.Location;
import com.marcinmajkowski.kawasakirobotsrestapi.service.KawasakiRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RobotController {

    private final KawasakiRobotService kawasakiRobotService;

    @Autowired
    public RobotController(KawasakiRobotService kawasakiRobotService) {
        this.kawasakiRobotService = kawasakiRobotService;
    }

    @RequestMapping("/")
    String model() {
        return kawasakiRobotService.getModel();
    }

    @RequestMapping("/tcp")
    Location toolCenterPoint() {
        return kawasakiRobotService.getToolCenterPoint();
    }

    @RequestMapping("/status")
    String status() {
        return kawasakiRobotService.getStatus();
    }

}

