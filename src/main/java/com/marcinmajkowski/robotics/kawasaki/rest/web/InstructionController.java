package com.marcinmajkowski.robotics.kawasaki.rest.web;

import com.marcinmajkowski.robotics.kawasaki.rest.domain.*;
import com.marcinmajkowski.robotics.kawasaki.rest.service.InstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/robots/{id}/instructions")
public class InstructionController {
    private InstructionService instructionService;

    @Autowired
    public InstructionController(InstructionService instructionService) {
        this.instructionService = instructionService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Instruction execute(@PathVariable int id, @RequestBody Instruction instruction) {
        InstructionParameter parameter = instruction.getParameter();
        String keyword = instruction.getKeyword();
        if (isNull(keyword)) {
            throw new NullPointerException("keyword is null");
        } else if ("jmove".equalsIgnoreCase(keyword)) {
            LocationVariable location = parameter.getLocation();
            Integer clampNumber = parameter.getClampNumber();
            if (isValid(location)) {
                instructionService.jmove(location, clampNumber);
            } else {
                throw new RuntimeException(); //TODO
            }
        } else if ("lmove".equalsIgnoreCase(keyword)) {
            LocationVariable location = parameter.getLocation();
            Integer clampNumber = parameter.getClampNumber();
            if (isValid(location)) {
                instructionService.lmove(location, clampNumber);
            } else {
                throw new RuntimeException(); //TODO
            }
        } else if ("delay".equalsIgnoreCase(keyword)) {
            Double time = parameter.getTime();
            if (nonNull(time)) {
                instructionService.delay(time);
            } else {
                throw new RuntimeException(); //TODO
            }
        } else if ("stable".equalsIgnoreCase(keyword)) {
            Double time = parameter.getTime();
            if (nonNull(time)) {
                instructionService.stable(time);
            } else {
                throw new RuntimeException(); //TODO
            }
        } else if ("jappro".equalsIgnoreCase(keyword)) {
            LocationVariable location = parameter.getLocation();
            Double distance = parameter.getDistance();
            if (isValid(location) && nonNull(distance)) {
                instructionService.jappro(location, distance);
            } else {
                throw new RuntimeException(); //TODO
            }
        } else if ("lappro".equalsIgnoreCase(keyword)) {
            LocationVariable location = parameter.getLocation();
            Double distance = parameter.getDistance();
            if (isValid(location) && nonNull(distance)) {
                instructionService.lappro(location, distance);
            } else {
                throw new RuntimeException(); //TODO
            }
        } else if ("jdepart".equalsIgnoreCase(keyword)) {
            Double distance = parameter.getDistance();
            if (nonNull(distance)) {
                instructionService.jdepart(distance);
            } else {
                throw new RuntimeException(); //TODO
            }
        } else if ("ldepart".equalsIgnoreCase(keyword)) {
            Double distance = parameter.getDistance();
            if (nonNull(distance)) {
                instructionService.ldepart(distance);
            } else {
                throw new RuntimeException(); //TODO
            }
        } else if ("home".equalsIgnoreCase(keyword)) {
            Integer homePoseNumber = parameter.getHomePoseNumber();
            if (nonNull(homePoseNumber)) {
                instructionService.home();
            } else {
                throw new RuntimeException();
            }
        } else if ("drive".equalsIgnoreCase(keyword)) {
            Integer jointNumber = parameter.getJointNumber();
            Double displacement = parameter.getDisplacement();
            Double speed = parameter.getSpeed();
            if (nonNull(jointNumber) && nonNull(displacement)) {
                instructionService.drive(jointNumber, displacement, speed);
            } else {
                throw new RuntimeException();
            }
        } else if ("draw".equalsIgnoreCase(keyword)) {
            Transformation transformation = parameter.getTransformation();
            instructionService.draw(transformation);
        } else if ("tdraw".equalsIgnoreCase(keyword)) {
            Transformation transformation = parameter.getTransformation();
            instructionService.tdraw(transformation);
        } else if ("align".equalsIgnoreCase(keyword)) {
            instructionService.align();
        } else if ("hmove".equalsIgnoreCase(keyword)) {
            LocationVariable location = parameter.getLocation();
            Integer clampNumber = parameter.getClampNumber();
            if (isValid(location)) {
                instructionService.hmove(location, clampNumber);
            } else {
                throw new RuntimeException(); //TODO
            }
        } else if ("xmove".equalsIgnoreCase(keyword)) {
            String mode = parameter.getMode();
            LocationVariable location = parameter.getLocation();
            Integer signalNumber = parameter.getSignalNumber();
            if (isValid(location) && nonNull(signalNumber)) {
                instructionService.xmove(mode, location, signalNumber);
            } else {
                throw new RuntimeException();
            }
        } else if ("c1move".equalsIgnoreCase(keyword)) {
            LocationVariable location = parameter.getLocation();
            Integer clampNumber = parameter.getClampNumber();
            if (isValid(location)) {
                instructionService.c1move(location, clampNumber);
            } else {
                throw new RuntimeException(); //TODO
            }
        } else if ("c2move".equalsIgnoreCase(keyword)) {
            LocationVariable location = parameter.getLocation();
            Integer clampNumber = parameter.getClampNumber();
            if (isValid(location)) {
                instructionService.c2move(location, clampNumber);
            } else {
                throw new RuntimeException(); //TODO
            }
        }

        return instruction;
    }

    private static boolean isValid(LocationVariable location) {
        String name = location.getName();
        Transformation transformation = location.getTransformation();
        JointDisplacement jointDisplacement = location.getJointDisplacement();
        int numberOfNonNul = (nonNull(name) ? 1 : 0)
                + (nonNull(transformation) ? 1 : 0)
                + (nonNull(jointDisplacement) ? 1 : 0);
        return numberOfNonNul == 1;
    }
}

