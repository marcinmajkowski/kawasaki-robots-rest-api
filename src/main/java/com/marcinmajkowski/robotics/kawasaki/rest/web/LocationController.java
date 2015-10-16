package com.marcinmajkowski.robotics.kawasaki.rest.web;

import com.marcinmajkowski.robotics.kawasaki.rest.domain.Location;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.Transformation;
import com.marcinmajkowski.robotics.kawasaki.rest.service.KawasakiRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
    //TODO multidimensional arrays
    //TODO store current pose in new variable (here pose vs. here #pose !)
    //TODO relatives: HERE plate+object+pickup (defines pickup)

    private final KawasakiRobotService kawasakiRobotService;

    @Autowired
    public LocationController(KawasakiRobotService kawasakiRobotService) {
        this.kawasakiRobotService = kawasakiRobotService;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Location getByName(@PathVariable String name) {
        //TODO handle arrays
        return kawasakiRobotService.getLocationByName(name);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.POST, consumes = "application/json")
    public void add(@RequestBody Transformation transformation,
                    @PathVariable String name) {
        //TODO handle arrays
        //TODO different result codes for different results
        kawasakiRobotService.addLocation(new Location(name, transformation, null));
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String name) {
        kawasakiRobotService.deleteLocation(name);
    }

    @RequestMapping(/*value = "/", */method = RequestMethod.GET)
    public List<String> getAllNames() {
        return kawasakiRobotService.getAllLocationNames();
    }

    @RequestMapping(value = "/here", method = RequestMethod.GET)
    Location toolCenterPoint() {
        return kawasakiRobotService.getToolCenterPoint();
    }

    @RequestMapping(value = "/null", method = RequestMethod.GET)
    Location nullLocation() {
        return new Location("null", new Transformation(0, 0, 0, 0, 0, 0), null);
    }

    //TODO /tool
    //TODO /base
}
