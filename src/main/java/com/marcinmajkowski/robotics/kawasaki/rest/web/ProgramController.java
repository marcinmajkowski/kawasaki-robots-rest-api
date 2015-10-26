package com.marcinmajkowski.robotics.kawasaki.rest.web;

import com.marcinmajkowski.robotics.kawasaki.rest.domain.Program;
import com.marcinmajkowski.robotics.kawasaki.rest.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/robots/{id}/programs")
public class ProgramController {
    private final ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    //TODO
    @RequestMapping
    public Set<Program> getAll() {
        return programService.getAll();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public Program put(@PathVariable String name, @RequestBody Program program) {
        Program previousProgram = programService.get(name);

        if (isNull(previousProgram)) {
            programService.put(name, program);
        } else {
            Program updatedProgram = getUpdatedProgram(program, previousProgram);
            programService.put(name, updatedProgram);
        }

        return previousProgram;
    }

    private static Program getUpdatedProgram(Program program, Program previousProgram) {
        if (isNull(previousProgram)) {
            return program;
        } else if (isNull(program)) {
            return previousProgram;
        }

        String name = isNull(program.getName()) ? previousProgram.getName() : program.getName();
        String content = isNull(program.getContent()) ? previousProgram.getContent() : program.getContent();
        boolean isRunning = program.isRunning() || previousProgram.isRunning(); //FIXME can start program unexpectedly!

        Program result = new Program();
        result.setName(name);
        result.setContent(content);
        result.setIsRunning(isRunning);

        return result;
    }
}
