package com.marcinmajkowski.robotics.kawasaki.rest.web;

import com.marcinmajkowski.robotics.kawasaki.rest.domain.Name;
import com.marcinmajkowski.robotics.kawasaki.rest.service.KawasakiRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/reals")
public class RealController {
    private final KawasakiRobotService kawasakiRobotService;

    @Autowired
    public RealController(KawasakiRobotService kawasakiRobotService) {
        this.kawasakiRobotService = kawasakiRobotService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Name> getAllNames() {
        List<String> realNames = kawasakiRobotService.getAllRealNames();
        List<Name> results = new ArrayList<>(realNames.size());
        for (String realName : realNames) {
            Name name = new Name(realName);
            name.add(linkTo(RealController.class).slash(realName).withSelfRel());
            results.add(name);
        }
        return results;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Double getByName(@PathVariable String name) {
        //TODO handle arrays
        return kawasakiRobotService.getRealByName(name);
    }

    //TODO

    //TODO random
}
