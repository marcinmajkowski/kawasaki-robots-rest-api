package com.marcinmajkowski.robotics.kawasaki.rest.service;

public class UnknownRobotResponseException extends Exception {
    public UnknownRobotResponseException() {
    }

    public UnknownRobotResponseException(String message) {
        super(message);
    }

    public UnknownRobotResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownRobotResponseException(Throwable cause) {
        super(cause);
    }

    public UnknownRobotResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
