package com.marcinmajkowski.robotics.kawasaki.rest.service;

import com.marcinmajkowski.robotics.kawasaki.client.tcp.TcpClient;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.RealArrayElement;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.RealVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RealService {
    private final TcpClient tcpClient;

    @Autowired
    public RealService(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public Set<RealVariable> getAll() {
        String response = null;
        try {
            response = tcpClient.getResponse("list/r");
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* Response:
        >list/r
        Real
        a        = 1, ar[5]    = 6, ar[8]    = 90, b        = 2, c        = 3
        d        = 4, g        = 4, h        = 5, i        = 2.91342e+07, j        = 5
        k        = 2.34012e+08, minus    = 3e-10, numer    = 3.4, sama     = 0.3
        ujemna   = -3, y        = 3.45235e+06
        */
        //TODO NullPointerException
        Set<RealVariable> results = new HashSet<>();
        Matcher matcher = Pattern.compile("(\\w+)(?:\\[(\\d+)\\])? *= *(-?\\d+\\.?\\d*e?(?:\\+|-)?\\d*)").matcher(response);
        while (matcher.find()) {
            String name = matcher.group(1);
            Double value = Double.valueOf(matcher.group(3));
            if (matcher.group(2) != null) {
                int index = Integer.valueOf(matcher.group(2));
                results.add(new RealArrayElement(name, index, value));
            } else {
                RealVariable realVariable = new RealVariable();
                realVariable.setName(name);
                realVariable.setValue(value);
                results.add(realVariable);
            }
        }
//        String[] tokens = response.split("\\s+");
//        List<String> result = Arrays.asList(tokens).subList(tokens.length > 3 ? 3 : tokens.length, tokens.length);

        return results;
    }

    public RealVariable get(String name) throws ResourceNotFoundException {
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
            double value = Double.parseDouble(matcher.group(1));
            RealVariable realVariable = new RealVariable();
            realVariable.setName(name);
            realVariable.setValue(value);
            result = realVariable;
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

    public RealVariable put(RealVariable real) {
        //TODO
        return null;
    }
}
