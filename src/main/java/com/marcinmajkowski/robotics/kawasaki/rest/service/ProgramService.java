package com.marcinmajkowski.robotics.kawasaki.rest.service;

import com.marcinmajkowski.robotics.kawasaki.client.tcp.TcpClient;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProgramService {
    private final TcpClient tcpClient;

    @Autowired
    public ProgramService(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public Set<Program> getAll() {
        //TODO
        return null;
    }

    public Program get(String name) {
        //TODO
        return null;
    }

    public Program put(String name, Program program) {
        //TODO
        //if isRunning then ex (temporary)
        return null;
    }
}
