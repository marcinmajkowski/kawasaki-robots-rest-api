package com.marcinmajkowski.kawasakirobotsrestapi.domain;

/**
 * Created by Marcin on 2015-08-20.
 */
public final class Location {
    private final String name;
    private final double x;
    private final double y;
    private final double z;
    private final double o;
    private final double a;
    private final double t;
    private final double[] joints;

    public Location(String name, double x, double y, double z, double o, double a, double t, double[] joints) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.o = o;
        this.a = a;
        this.t = t;
        this.joints = joints == null ? null : joints.clone();
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getO() {
        return o;
    }

    public double getA() {
        return a;
    }

    public double getT() {
        return t;
    }

    public double[] getJoints() {
        return joints == null ? null : joints.clone();
    }
}
