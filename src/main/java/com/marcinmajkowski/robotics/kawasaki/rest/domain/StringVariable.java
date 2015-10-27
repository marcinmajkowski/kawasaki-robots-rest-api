package com.marcinmajkowski.robotics.kawasaki.rest.domain;

import org.springframework.hateoas.ResourceSupport;

public class StringVariable extends ResourceSupport {
    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
