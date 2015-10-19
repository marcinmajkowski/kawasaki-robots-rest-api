package com.marcinmajkowski.robotics.kawasaki.rest.web;

import com.marcinmajkowski.robotics.kawasaki.rest.domain.LocationVariable;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.Transformation;
import com.marcinmajkowski.robotics.kawasaki.rest.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/locations")
public class LocationController {
    //TODO multidimensional arrays
    //TODO store current pose in new variable (here pose vs. here #pose !)
    //TODO relatives: HERE plate+object+pickup (defines pickup)

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public LocationVariable getByName(@PathVariable String name) {
        //TODO handle arrays
        return locationService.getLocationByName(name);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.POST, consumes = "application/json")
    public void add(@RequestBody Transformation transformation,
                    @PathVariable String name) {
        //TODO handle arrays
        //TODO different result codes for different results
        locationService.addLocation(new LocationVariable(name, transformation, null));
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String name) {
        locationService.deleteLocation(name);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<String> getAllNames() {
        return locationService.getAllLocationNames();
    }

    @RequestMapping(value = "/here", method = RequestMethod.GET)
    LocationVariable toolCenterPoint() {
        LocationVariable locationVariable = locationService.getToolCenterPoint();
        locationVariable.add(linkTo(methodOn(LocationController.class).toolCenterPoint()).withSelfRel());
        return locationVariable;
    }

    @RequestMapping(value = "/null", method = RequestMethod.GET)
    LocationVariable nullLocation() {
        LocationVariable locationVariable = new LocationVariable("null", new Transformation(0, 0, 0, 0, 0, 0), null);
        locationVariable.add(linkTo(methodOn(LocationController.class).nullLocation()).withSelfRel());
        return locationVariable;
    }

    //TODO /tool
    //TODO /base
}
