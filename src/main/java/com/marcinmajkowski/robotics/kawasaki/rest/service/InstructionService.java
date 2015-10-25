package com.marcinmajkowski.robotics.kawasaki.rest.service;

import com.marcinmajkowski.robotics.kawasaki.client.tcp.TcpClient;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.JointDisplacement;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.LocationVariable;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.Transformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Service
public class InstructionService {
    private final TcpClient tcpClient;

    @Autowired
    public InstructionService(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public void drive(Integer jointNumber, Double displacement, Double speed) {
        requireNonNull(jointNumber);
        requireNonNull(displacement);
        try {
            tcpClient.getResponse("do drive " + jointNumber + ", " + displacement + (speed != null ? ", " + speed : ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void jmove(LocationVariable location, Integer clampNumber) {
        requireNonNull(location);
        StringBuilder command = new StringBuilder("do jmove ");
        if (nonNull(location.getName())) {
            String name = location.getName();
            command.append(name);
        } else if (nonNull(location.getJointDisplacement())) {
            JointDisplacement jointDisplacement = location.getJointDisplacement();
            command.append(jointDisplacement.toInstructionParameter());
        } else if (nonNull(location.getTransformation())) {
            Transformation transformation = location.getTransformation();
            command.append(transformation.toInstructionParameter());
        } else {
            throw new IllegalArgumentException();
        }

        if (nonNull(clampNumber)) {
            command.append(" ").append(clampNumber);
        }

        try {
            tcpClient.getResponse(command.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lmove(LocationVariable location, Integer clampNumber) {

    }

    public void delay(Double time) {

    }

    public void stable(Double time) {

    }

    public void jappro(LocationVariable location, Double distance) {

    }

    public void lappro(LocationVariable location, Double distance) {

    }

    public void jdepart(Double distance) {

    }

    public void ldepart(Double distance) {

    }

    public void home() {

    }

    public void draw(Transformation transformation) {

    }

    public void tdraw(Transformation transformation) {

    }

    public void align() {

    }

    public void hmove(LocationVariable location, Integer clampNumber) {

    }

    public void xmove(String mode, LocationVariable location, Integer signalNumber) {

    }

    public void c1move(LocationVariable location, Integer clampNumber) {

    }

    public void c2move(LocationVariable location, Integer clampNumber) {

    }
}
