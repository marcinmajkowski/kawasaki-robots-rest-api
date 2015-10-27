package com.marcinmajkowski.robotics.kawasaki.rest.domain;

//TODO this class probably will be removed soon and regular RealVariable will serve as array element
public class RealArrayElement extends RealVariable {
    private int index;

    public RealArrayElement() {
    }

    public RealArrayElement(String name, int index, double value) {
        setName(name);
        setValue(value);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
