package com.marcinmajkowski.robotics.kawasaki.rest.web;

import com.marcinmajkowski.robotics.kawasaki.rest.domain.JointDisplacement;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.LocationVariable;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.Transformation;
import com.marcinmajkowski.robotics.kawasaki.rest.service.LocationService;
import com.marcinmajkowski.robotics.kawasaki.rest.service.ResourceNotFoundException;
import com.marcinmajkowski.robotics.kawasaki.rest.service.UnknownRobotResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/robots/{id}/locations")
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
    public LocationVariable getByName(@PathVariable String name) throws ResourceNotFoundException, UnknownRobotResponseException {
        //TODO handle arrays
        LocationVariable location = locationService.get(name);
        if (location == null) {
            throw new ResourceNotFoundException();
        }
        return location;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public LocationVariable put(@RequestBody LocationVariable location,
                                @PathVariable String name) {
        if (nonNull(location.getJointDisplacement()) && nonNull(location.getTransformation())) {
            throw new RuntimeException("umbiguity");
        }

        if (nonNull(location.getJointDisplacement())) {
            JointDisplacement jointDisplacement = location.getJointDisplacement();
            return locationService.put(name, jointDisplacement);
        } else if (nonNull(location.getTransformation())) {
            Transformation transformation = location.getTransformation();
            return locationService.put(name, transformation);
        }
        //TODO handle arrays
        //TODO different result codes for different results
        return null;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String name) {
        locationService.remove(name);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<String> getAllNames() {
        return locationService.getAllNames();
    }

    @RequestMapping(value = "/here", method = RequestMethod.GET)
    LocationVariable toolCenterPoint() {
        LocationVariable locationVariable = locationService.getToolCenterPoint();
        locationVariable.add(linkTo(methodOn(LocationController.class).toolCenterPoint()).withSelfRel());
        return locationVariable;
    }

    @RequestMapping(value = "/null", method = RequestMethod.GET)
    LocationVariable nullLocation() {
        Transformation transformation = new Transformation();
        transformation.setX(0);
        transformation.setY(0);
        transformation.setZ(0);
        transformation.setO(0);
        transformation.setA(0);
        transformation.setT(0);

        LocationVariable locationVariable = new LocationVariable();
        locationVariable.setName("null");
        locationVariable.setTransformation(transformation);
        locationVariable.add(linkTo(methodOn(LocationController.class).nullLocation()).withSelfRel());

        return locationVariable;
    }

    //TODO /tool
    //TODO /base
}
