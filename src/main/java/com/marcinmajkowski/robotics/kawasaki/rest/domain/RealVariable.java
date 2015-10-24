package com.marcinmajkowski.robotics.kawasaki.rest.domain;

import org.springframework.hateoas.ResourceSupport;

public class RealVariable extends ResourceSupport {
    private String name;
    private Double value;

    public RealVariable(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    public RealVariable() {
    }

    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RealVariable that = (RealVariable) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
