package com.marcinmajkowski.robotics.kawasaki.rest.web;

import com.marcinmajkowski.robotics.kawasaki.rest.service.StringService;
import com.marcinmajkowski.robotics.kawasaki.rest.service.UnknownRobotResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/strings")
public class StringController {
    private final StringService stringService;

    @Autowired
    public StringController(StringService stringService) {
        this.stringService = stringService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<String> nameSet() {
        return stringService.nameSet();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public ResponseEntity<String> put(@RequestBody String value,
                                      @PathVariable String name) throws UnknownRobotResponseException {
        //TODO handle arrays
        String previousValue = stringService.put(name, value);
        HttpStatus status = previousValue == null ? HttpStatus.CREATED : HttpStatus.OK;
        return new ResponseEntity<>(previousValue, status);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String get(@PathVariable String name) {
        return stringService.get(name);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public String remove(@PathVariable String name) {
        //TODO handle status
        return stringService.remove(name);
    }

    //TODO
}
