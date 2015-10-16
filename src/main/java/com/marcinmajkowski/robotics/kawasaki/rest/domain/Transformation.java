package com.marcinmajkowski.robotics.kawasaki.rest.domain;

public final class Transformation {
    private double x;
    private double y;
    private double z;
    private double o;
    private double a;
    private double t;

    public Transformation(double x, double y, double z, double o, double a, double t) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.o = o;
        this.a = a;
        this.t = t;
    }

    public Transformation() {
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getO() {
        return o;
    }

    public void setO(double o) {
        this.o = o;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }
}
