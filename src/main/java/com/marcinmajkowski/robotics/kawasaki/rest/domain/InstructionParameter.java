package com.marcinmajkowski.robotics.kawasaki.rest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstructionParameter {
    private Integer jointNumber;

    private Double displacement;

    private Double speed;

    private LocationVariable location;

    private Integer clampNumber;

    private Double time;

    private Double distance;

    private Integer homePoseNumber;

    private Transformation transformation;

    private Integer signalNumber;

    private String mode;

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

    public LocationVariable getLocation() {
        return location;
    }

    public void setLocation(LocationVariable location) {
        this.location = location;
    }

    public Integer getClampNumber() {
        return clampNumber;
    }

    public void setClampNumber(Integer clampNumber) {
        this.clampNumber = clampNumber;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getHomePoseNumber() {
        return homePoseNumber;
    }

    public void setHomePoseNumber(Integer homePoseNumber) {
        this.homePoseNumber = homePoseNumber;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }

    public Integer getSignalNumber() {
        return signalNumber;
    }

    public void setSignalNumber(Integer signalNumber) {
        this.signalNumber = signalNumber;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
