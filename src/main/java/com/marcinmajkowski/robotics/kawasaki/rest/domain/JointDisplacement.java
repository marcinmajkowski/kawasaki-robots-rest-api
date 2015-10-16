package com.marcinmajkowski.robotics.kawasaki.rest.domain;

public class JointDisplacement {
    private final double[] joints;

    public JointDisplacement(double[] joints) {
        this.joints = joints == null ? null : joints.clone();
    }

    public double[] getJoints() {
        return joints == null ? null : joints.clone();
    }
}
