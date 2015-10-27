package com.marcinmajkowski.robotics.kawasaki.rest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.ResourceSupport;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationVariable /* PoseVariable */ extends ResourceSupport {
    private String name;

    private Transformation transformation;

    private JointDisplacement jointDisplacement;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }

    public JointDisplacement getJointDisplacement() {
        return jointDisplacement;
    }

    public void setJointDisplacement(JointDisplacement jointDisplacement) {
        this.jointDisplacement = jointDisplacement;
    }
}
