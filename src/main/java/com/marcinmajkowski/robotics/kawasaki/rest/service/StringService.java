package com.marcinmajkowski.robotics.kawasaki.rest.service;

import com.marcinmajkowski.robotics.kawasaki.client.tcp.TcpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StringService {
    private final TcpClient tcpClient;

    @Autowired
    public StringService(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }
}
