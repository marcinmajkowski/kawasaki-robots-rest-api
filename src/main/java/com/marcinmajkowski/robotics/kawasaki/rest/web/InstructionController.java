package com.marcinmajkowski.robotics.kawasaki.rest.web;

import com.marcinmajkowski.robotics.kawasaki.rest.domain.Instruction;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.InstructionParameter;
import com.marcinmajkowski.robotics.kawasaki.rest.service.InstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        switch (instruction.getKeyword().toLowerCase()) {
            case "drive":
                Integer jointNumber = parameter.getJointNumber();
                Double displacement = parameter.getDisplacement();
                Double speed = parameter.getSpeed();
                instructionService.drive(jointNumber, displacement, speed);
                break;
        }
        return instruction;
    }
}
