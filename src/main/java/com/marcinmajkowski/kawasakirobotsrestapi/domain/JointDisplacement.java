package com.marcinmajkowski.kawasakirobotsrestapi.domain;

/**
 * Created by Marcin on 2015-08-25.
 */
public class JointDisplacement {
    private final double[] joints;

    public JointDisplacement(double[] joints) {
        this.joints = joints == null ? null : joints.clone();
    }

    public double[] getJoints() {
        return joints == null ? null : joints.clone();
    }
}
