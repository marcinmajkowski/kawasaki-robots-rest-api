package com.marcinmajkowski.robotics.kawasaki.rest.web;

import com.marcinmajkowski.robotics.kawasaki.rest.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class RobotController {

    private final RobotService robotService;

    @Autowired
    public RobotController(RobotService robotService) {
        this.robotService = robotService;
    }

    @RequestMapping
    Map<String, URI> root() {
        HashMap<String, URI> resources = new HashMap<>();
        resources.put("programs", linkTo(ProgramController.class).toUri());
        resources.put("locations", linkTo(LocationController.class).toUri());
        resources.put("reals", linkTo(RealController.class).toUri());
        resources.put("strings", linkTo(StringController.class).toUri());
        return resources;
    }

    @RequestMapping("/status")
    String status() {
        return robotService.getStatus();
    }

    //TODO temporary
    @RequestMapping(value = "/load", method = RequestMethod.POST)
    @ResponseBody
    String load(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("temp")));
                stream.write(bytes);
                stream.close();
                robotService.load("temp");
                return "You successfully uploaded file!";
            } catch (IOException e) {
                return "You failed to upload file - " + e.getMessage();
            }
        }
        return "Empty file";
    }

    @RequestMapping(value = "/data/{fileName:.+}", method = RequestMethod.GET)
    ResponseEntity<String> data(@PathVariable("fileName") String fileName,
                                @RequestParam(value = "only", required = false) String only) {
        String[] types = (only != null ? only : "").split(",");
        Set<RobotService.SaveCommandArgument> arguments = new HashSet<>();
        for (String type : types) {
            switch (type.toLowerCase()) {
                case "programs":
                    arguments.add(RobotService.SaveCommandArgument.PROGRAMS);
                    break;
                case "locations":
                    arguments.add(RobotService.SaveCommandArgument.LOCATIONS);
                    break;
                case "reals":
                    arguments.add(RobotService.SaveCommandArgument.REALS);
                    break;
                case "strings":
                    arguments.add(RobotService.SaveCommandArgument.STRINGS);
                    break;
                case "auxiliary-information":
                    arguments.add(RobotService.SaveCommandArgument.AUXILIARY_INFORMATION);
                    break;
                case "system-data":
                    arguments.add(RobotService.SaveCommandArgument.SYSTEM_DATA);
                    break;
                case "robot-data":
                    arguments.add(RobotService.SaveCommandArgument.ROBOT_DATA);
                    break;
                case "error-log-data":
                    arguments.add(RobotService.SaveCommandArgument.ERROR_LOG_DATA);
                    break;
            }
        }
        String response = robotService.getData(arguments.size() > 0 ?
                EnumSet.copyOf(arguments) :
                EnumSet.noneOf(RobotService.SaveCommandArgument.class));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }
}
