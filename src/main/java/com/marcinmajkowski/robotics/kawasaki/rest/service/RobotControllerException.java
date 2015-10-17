package com.marcinmajkowski.robotics.kawasaki.rest.service;

public class RobotControllerException extends Exception {
    private final String code;
    private final String description;

    public RobotControllerException(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
