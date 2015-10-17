package com.marcinmajkowski.robotics.kawasaki.rest.web;

import com.marcinmajkowski.robotics.kawasaki.rest.service.RobotControllerException;

public class ResourceNotFoundException extends RobotControllerException {
    public ResourceNotFoundException(String code, String description) {
        super(code, description);
    }
}
