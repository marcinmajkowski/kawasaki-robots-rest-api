package com.marcinmajkowski.robotics.kawasaki.rest.web;

import com.marcinmajkowski.robotics.kawasaki.rest.domain.Name;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.RealVariable;
import com.marcinmajkowski.robotics.kawasaki.rest.service.RealService;
import com.marcinmajkowski.robotics.kawasaki.rest.service.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/reals")
public class RealController {
    private final RealService realService;
    //TODO nolink
    //TODO embedded

    @Autowired
    public RealController(RealService realService) {
        this.realService = realService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Name> getAllNames() {
        List<String> realNames = realService.getAllRealNames();
        List<Name> results = new ArrayList<>(realNames.size());
        for (String realName : realNames) {
            Name name = new Name(realName);
            name.add(linkTo(RealController.class).slash(realName).withSelfRel());
            results.add(name);
        }
        return results;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public RealVariable getByName(@PathVariable String name) throws ResourceNotFoundException {
        //TODO handle arrays
        RealVariable realVariable = realService.getRealByName(name);
        realVariable.add(linkTo(methodOn(RealController.class).getByName(name)).withSelfRel());
        return realVariable;
    }

    //TODO

    //TODO random
}
