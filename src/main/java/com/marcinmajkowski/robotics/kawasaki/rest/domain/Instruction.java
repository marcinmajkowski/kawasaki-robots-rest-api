package com.marcinmajkowski.robotics.kawasaki.rest.domain;

public class Instruction {
    private String keyword;

    private InstructionParameter parameter;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public InstructionParameter getParameter() {
        return parameter;
    }

    public void setParameter(InstructionParameter parameter) {
        this.parameter = parameter;
    }
}
