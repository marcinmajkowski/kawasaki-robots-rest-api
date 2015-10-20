package com.marcinmajkowski.robotics.kawasaki.rest.domain;

import org.springframework.hateoas.ResourceSupport;

public class LocationVariable /* PoseVariable */ extends ResourceSupport {
    private final String name;
    private final Transformation transformation;
    private final JointDisplacement jointDisplacement;

    public LocationVariable(String name, Transformation transformation, JointDisplacement jointDisplacement) {
        this.name = name;
        this.transformation = transformation;
        this.jointDisplacement = jointDisplacement;
    }

    public String getName() {
        return name;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public JointDisplacement getJointDisplacement() {
        return jointDisplacement;
    }
}
