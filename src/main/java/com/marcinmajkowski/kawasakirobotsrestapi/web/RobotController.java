package com.marcinmajkowski.kawasakirobotsrestapi.web;

import com.marcinmajkowski.kawasakirobotsrestapi.service.KawasakiRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@RestController
public class RobotController {

    private final KawasakiRobotService kawasakiRobotService;

    @Autowired
    public RobotController(KawasakiRobotService kawasakiRobotService) {
        this.kawasakiRobotService = kawasakiRobotService;
    }

    @RequestMapping("/status")
    String status() {
        return kawasakiRobotService.getStatus();
    }

    @RequestMapping(value = "/data/{fileName:.+}", method = RequestMethod.GET)
    ResponseEntity<String> data(@PathVariable("fileName") String fileName,
                                @RequestParam(value = "only", required = false) String only) {
        String[] types = (only != null ? only : "").split(",");
        Set<KawasakiRobotService.SaveCommandArgument> arguments = new HashSet<>();
        for (String type : types) {
            switch (type.toLowerCase()) {
                case "programs":
                    arguments.add(KawasakiRobotService.SaveCommandArgument.PROGRAMS);
                    break;
                case "locations":
                    arguments.add(KawasakiRobotService.SaveCommandArgument.LOCATIONS);
                    break;
                case "reals":
                    arguments.add(KawasakiRobotService.SaveCommandArgument.REALS);
                    break;
                case "strings":
                    arguments.add(KawasakiRobotService.SaveCommandArgument.STRINGS);
                    break;
                case "auxiliary-information":
                    arguments.add(KawasakiRobotService.SaveCommandArgument.AUXILIARY_INFORMATION);
                    break;
                case "system-data":
                    arguments.add(KawasakiRobotService.SaveCommandArgument.SYSTEM_DATA);
                    break;
                case "robot-data":
                    arguments.add(KawasakiRobotService.SaveCommandArgument.ROBOT_DATA);
                    break;
                case "error-log-data":
                    arguments.add(KawasakiRobotService.SaveCommandArgument.ERROR_LOG_DATA);
                    break;
            }
        }
        String response = kawasakiRobotService.getData(arguments.size() > 0 ?
                EnumSet.copyOf(arguments) :
                EnumSet.noneOf(KawasakiRobotService.SaveCommandArgument.class));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        return new ResponseEntity<String>(response, headers, HttpStatus.CREATED);
    }
}
