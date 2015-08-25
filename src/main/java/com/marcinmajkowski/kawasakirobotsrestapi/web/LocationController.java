package com.marcinmajkowski.kawasakirobotsrestapi.web;

import com.marcinmajkowski.kawasakirobotsrestapi.domain.Location;
import com.marcinmajkowski.kawasakirobotsrestapi.domain.Transformation;
import com.marcinmajkowski.kawasakirobotsrestapi.service.KawasakiRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path = "/{name}", method = RequestMethod.POST, consumes = "application/json")
    public void add(@RequestBody Transformation transformation,
                    @PathVariable String name) {
        //TODO
        System.out.println(transformation + " " + name);
        kawasakiRobotService.addLocation(new Location(name, transformation, null));
    }

    //TODO /locations (without / at the end) doesn't work
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public List<String> getAllNames() {
        return kawasakiRobotService.getAllLocationNames();
    }

    @RequestMapping(path = "/here", method = RequestMethod.GET)
    Location toolCenterPoint() {
        return kawasakiRobotService.getToolCenterPoint();
    }
}
