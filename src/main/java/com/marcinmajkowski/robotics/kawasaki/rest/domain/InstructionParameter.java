package com.marcinmajkowski.robotics.kawasaki.rest.domain;

public class InstructionParameter {
    // drive:
    private Integer jointNumber;

    private Double displacement;

    private Double speed;

    public Integer getJointNumber() {
        return jointNumber;
    }

    public void setJointNumber(Integer jointNumber) {
        this.jointNumber = jointNumber;
    }

    public Double getDisplacement() {
        return displacement;
    }

    public void setDisplacement(Double displacement) {
        this.displacement = displacement;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }
}
