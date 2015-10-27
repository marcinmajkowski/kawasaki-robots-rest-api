package com.marcinmajkowski.robotics.kawasaki.rest.domain;

public class JointDisplacement {
    private double[] joints;

    public double[] getJoints() {
        return joints;
    }

    public void setJoints(double[] joints) {
        this.joints = joints;
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
