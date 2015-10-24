package com.marcinmajkowski.robotics.kawasaki.rest.domain;

public class RealArrayElement extends RealVariable {
    private int index;

    public RealArrayElement() {
    }

    public RealArrayElement(String name, int index, double value) {
        super(name, value);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
