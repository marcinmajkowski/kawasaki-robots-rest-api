package com.marcinmajkowski.robotics.kawasaki.rest.service;

import com.marcinmajkowski.robotics.kawasaki.client.tcp.TcpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StringService {
    private final TcpClient tcpClient;

    @Autowired
    public StringService(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public String put(String name, String value) throws UnknownRobotResponseException {
        String previousValue = get(name);

        String command = "$" + name + "=\"" + value + "\"";
        String response = null;
        try {
            response = tcpClient.getResponse(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Matcher matcher = Pattern.compile(Pattern.quote(command) + "\r\n(.+)\r\n", Pattern.DOTALL).matcher(response);
        /*
        --- Response:
        $name="content""
                        ^(P0101)Too many arguments.

        ---
         */
        if (matcher.matches()) {
            throw new UnknownRobotResponseException(matcher.group(1).trim());
        }

        return previousValue;
    }

    public String get(String name) {
        //TODO refactor
        String response = null;
        try {
            response = tcpClient.getResponse("list /s $" + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* Response:
        list /s $name
        String
        $name    = "This is variable value"

        */
        String value = null;
        Matcher matcher = Pattern.compile("\"(.*)\"").matcher(response);
        if (matcher.find()) {
            value = matcher.group(1);
        }

        return value;
    }

    public String remove(String name) {
        String previousValue = get(name);
        //TODO prevent injecting malicious command
        String command = "delete/s $" + name + "\r\n" + "1";
        try {
            tcpClient.getResponse(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* Whole process:
        delete /l p1
        Are you sure ? (Yes:1, No:0) 1

        */
        return previousValue;
    }

    public Set<String> nameSet() {
        Set<String> nameSet = new TreeSet<>();

        String response = null;
        try {
            response = tcpClient.getResponse("dir/s");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Matcher matcher = Pattern.compile("\\$(\\w+)").matcher(response);
        while (matcher.find()) {
            nameSet.add(matcher.group(1));
        }
        //TODO error handling

        return nameSet;
    }
}
