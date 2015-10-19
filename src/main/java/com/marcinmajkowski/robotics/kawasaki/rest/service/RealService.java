package com.marcinmajkowski.robotics.kawasaki.rest.service;

import com.marcinmajkowski.robotics.kawasaki.client.tcp.TcpClient;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.RealVariable;
import com.marcinmajkowski.robotics.kawasaki.rest.web.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RealService {
    private final TcpClient tcpClient;

    @Autowired
    public RealService(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public List<String> getAllRealNames() {
        String response = null;
        try {
            response = tcpClient.getResponse("dir /r");
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* Response:
        dir /r
        Real
         p1        p2        p3        p4        p5        p6[]

        */
        //TODO NullPointerException
        String[] tokens = response.split("\\s+");
        List<String> result = Arrays.asList(tokens).subList(tokens.length > 3 ? 3 : tokens.length, tokens.length);

        return result;
    }

    public RealVariable getRealByName(String name) throws ResourceNotFoundException {
        String response = null;
        try {
            response = tcpClient.getResponse("list /r " + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* Response:
        list /r k
        Real
        k        = 3

        */
        RealVariable result = null;
        Matcher matcher = Pattern.compile(Pattern.quote(name) + " += +(\\d+(?:\\.\\d+)?)").matcher(response);
        if (matcher.find()) {
            result = new RealVariable(name, Double.parseDouble(matcher.group(1)));
        } else {
            matcher = Pattern.compile("(\\(P\\d+\\).*?)\\r?\\n").matcher(response);
            if (!matcher.find()) {
                throw new ResourceNotFoundException();
            }
            String message = matcher.group(1);
            throw new ResourceNotFoundException(message);
        }
        return result;
    }
}
