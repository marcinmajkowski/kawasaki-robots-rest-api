package com.marcinmajkowski.robotics.kawasaki.rest.domain;

import java.util.List;

public class Robot {
    private int id;

    private List<Instruction> instructions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }
}
