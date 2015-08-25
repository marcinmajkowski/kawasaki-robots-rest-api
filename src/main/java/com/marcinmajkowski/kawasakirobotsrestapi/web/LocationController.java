package com.marcinmajkowski.kawasakirobotsrestapi.web;

import com.marcinmajkowski.kawasakirobotsrestapi.domain.Location;
import com.marcinmajkowski.kawasakirobotsrestapi.service.KawasakiRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Marcin on 2015-08-25.
 */
@RestController
@RequestMapping("/locations")
public class LocationController {

    private final KawasakiRobotService kawasakiRobotService;

    @Autowired
    public LocationController(KawasakiRobotService kawasakiRobotService) {
        this.kawasakiRobotService = kawasakiRobotService;
    }

    @RequestMapping(path = "/{name}", method = RequestMethod.GET)
    public Location getByName(@PathVariable String name) {
        //TODO handle arrays
        return kawasakiRobotService.getLocationByName(name);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public List<String> getAllNames() {
        return kawasakiRobotService.getAllLocationNames();
    }

    @RequestMapping(path = "/here", method = RequestMethod.GET)
    Location toolCenterPoint() {
        return kawasakiRobotService.getToolCenterPoint();
    }
}
