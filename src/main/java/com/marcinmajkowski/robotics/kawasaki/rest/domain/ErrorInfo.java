package com.marcinmajkowski.robotics.kawasaki.rest.domain;

import com.marcinmajkowski.robotics.kawasaki.rest.service.RobotControllerException;

public class ErrorInfo {
    public final String url;
    public final String ex;
    public final String code;

    public ErrorInfo(String url, RobotControllerException ex) {
        this.url = url;
        this.ex = ex.getLocalizedMessage();
        this.code = ex.getCode();
    }
}
