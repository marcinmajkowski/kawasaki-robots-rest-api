package com.marcinmajkowski.robotics.kawasaki.rest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.ResourceSupport;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationVariable /* PoseVariable */ extends ResourceSupport {
    private String name;
    private Transformation transformation;
    private JointDisplacement jointDisplacement;

    public LocationVariable(String name, Transformation transformation, JointDisplacement jointDisplacement) {
        this.name = name;
        this.transformation = transformation;
        this.jointDisplacement = jointDisplacement;
    }

    public LocationVariable() {
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
