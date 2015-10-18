package com.marcinmajkowski.robotics.kawasaki.rest.domain;

import org.springframework.hateoas.ResourceSupport;

public class RealVariable extends ResourceSupport {
    private final String name;
    private final Double value;

    public RealVariable(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }
}
