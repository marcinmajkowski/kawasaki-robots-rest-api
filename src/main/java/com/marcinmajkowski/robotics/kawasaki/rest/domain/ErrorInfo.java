package com.marcinmajkowski.robotics.kawasaki.rest.domain;

public class ErrorInfo {
    public final String url;
    public final String message;

    public ErrorInfo(String url, Exception exception) {
        this.url = url;
        this.message = exception.getLocalizedMessage();
    }
}
