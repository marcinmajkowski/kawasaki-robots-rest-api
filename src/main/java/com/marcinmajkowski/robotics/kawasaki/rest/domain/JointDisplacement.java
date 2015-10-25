package com.marcinmajkowski.robotics.kawasaki.rest.domain;

import static java.util.Objects.isNull;

public class JointDisplacement {
    private final double[] joints;

    public JointDisplacement(double[] joints) {
        this.joints = joints == null ? null : joints.clone();
    }

    public double[] getJoints() {
        return isNull(joints) ? null : joints.clone();
    }

    public String toInstructionParameter() {
        StringBuilder result = new StringBuilder("#ppoint(");
        boolean firstElement = true;
        for (double joint : joints) {
            if (!firstElement) {
                result.append(", ");
            }
            result.append(joint);
            firstElement = false;
        }
        result.append(")");
        return result.toString();
    }
}
