package com.marcinmajkowski.robotics.kawasaki.rest.service;

import com.marcinmajkowski.robotics.kawasaki.client.tcp.TcpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class InstructionService {
    private final TcpClient tcpClient;

    @Autowired
    public InstructionService(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public void drive(Integer jointNumber, Double displacement, Double speed) {
        Objects.requireNonNull(jointNumber);
        Objects.requireNonNull(displacement);
        try {
            tcpClient.getResponse("do drive " + jointNumber + ", " + displacement + (speed != null ? ", " + speed : ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
