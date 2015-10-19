package com.marcinmajkowski.robotics.kawasaki.rest.service;

import com.marcinmajkowski.robotics.kawasaki.client.tcp.TcpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.EnumSet;

@Service
public class RobotService {
    private final TcpClient tcpClient;

    @Autowired
    public RobotService(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public String getModel() {
        //TODO
        return "RS005L";
    }

    public String getStatus() {
        String response = null;
        try {
            response = tcpClient.getResponse("status");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public enum SaveCommandArgument {
        PROGRAMS("/p"),
        LOCATIONS("/l"),
        REALS("/r"),
        STRINGS("/s"),
        AUXILIARY_INFORMATION("/a"),
        SYSTEM_DATA("/sys"),
        ROBOT_DATA("/rob"),
        ERROR_LOG_DATA("/elog");

        private final String literal;

        SaveCommandArgument(String literal) {
            this.literal = literal;
        }

        public String getLiteral() {
            return literal;
        }
    }

    public String getData() {
        return getData(EnumSet.noneOf(SaveCommandArgument.class));
    }

    public String getData(EnumSet<SaveCommandArgument> arguments) {
        StringBuilder command = new StringBuilder();
        command.append("save");
        for (SaveCommandArgument argument : arguments) {
            command.append(argument.getLiteral());
        }
        command.append(" filename.as"); //TODO
        String response = null;
        try {
            response = tcpClient.getResponse(command.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    //TODO this is temporary
    public void load(String filename) {
        try {
            tcpClient.getResponse("load " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
