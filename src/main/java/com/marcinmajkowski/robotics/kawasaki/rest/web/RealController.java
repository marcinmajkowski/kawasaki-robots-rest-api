package com.marcinmajkowski.robotics.kawasaki.rest.web;

import com.marcinmajkowski.robotics.kawasaki.rest.domain.RealVariable;
import com.marcinmajkowski.robotics.kawasaki.rest.service.RealService;
import com.marcinmajkowski.robotics.kawasaki.rest.service.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
    public Set<RealVariable> getAll() {
        Set<RealVariable> result = realService.getAll();
        return result;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public RealVariable get(@PathVariable String name) throws ResourceNotFoundException {
        //TODO handle arrays
        RealVariable realVariable = realService.get(name);
//        realVariable.add(linkTo(methodOn(RealController.class).get(name)).withSelfRel());
        return realVariable;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public RealVariable put(@PathVariable String name, @RequestBody RealVariable real) {
        String requestBodyName = real.getName();
        if (requestBodyName != null && !requestBodyName.equalsIgnoreCase(name)) {
            throw new RuntimeException(); //TODO
        }
        real.setName(name);
        RealVariable previousReal = realService.put(real);
        return previousReal;
    }
    //TODO

    //TODO random
}
