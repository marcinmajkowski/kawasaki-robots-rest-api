package com.marcinmajkowski.robotics.kawasaki.rest.domain;

import java.util.List;
import java.util.Set;

public class Robot {
    private int id;

    private List<Instruction> instructions;

    private Set<Program> programs;

    private Set<StringVariable> strings;

    private Set<LocationVariable> locations;

    private Set<RealVariable> reals;

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

    public Set<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(Set<Program> programs) {
        this.programs = programs;
    }

    public Set<StringVariable> getStrings() {
        return strings;
    }

    public void setStrings(Set<StringVariable> strings) {
        this.strings = strings;
    }

    public Set<LocationVariable> getLocations() {
        return locations;
    }

    public void setLocations(Set<LocationVariable> locations) {
        this.locations = locations;
    }

    public Set<RealVariable> getReals() {
        return reals;
    }

    public void setReals(Set<RealVariable> reals) {
        this.reals = reals;
    }
}
