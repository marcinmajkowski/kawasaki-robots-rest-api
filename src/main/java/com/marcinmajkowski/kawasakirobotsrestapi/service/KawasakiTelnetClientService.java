package com.marcinmajkowski.kawasakirobotsrestapi.service;

import org.apache.commons.net.telnet.InvalidTelnetOptionException;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class KawasakiTelnetClientService {
    //TODO add timeouts in while loops

    private final String hostname;
    private final int port;
    private final String login;

    private final TelnetClient telnetClient = new TelnetClient();

    public KawasakiTelnetClientService() {
        telnetClient.registerNotifHandler(new KawasakiTelnetNotificationHandler());
        TerminalTypeOptionHandler terminalTypeOptionHandler =
                new TerminalTypeOptionHandler("VT100", true, false, true, false);
        try {
            telnetClient.addOptionHandler(terminalTypeOptionHandler);
        } catch (InvalidTelnetOptionException e) {
            System.err.println("Error registering option handlers: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties configFile = new Properties();
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream("robot_connection.properties")) {
            configFile.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hostname = configFile.getProperty("HOSTNAME");
        port = Integer.parseInt(configFile.getProperty("PORT"));
        login = configFile.getProperty("LOGIN");
    }

    public String getResponse(String command) {
        return (getResponses(command)).get(0);
    }

    public synchronized List<String> getResponses(String... commands) {
        if (!telnetClient.isConnected()) {
            connect();
        }
        List<String> responses = new ArrayList<>();

        for (String command : commands) {
            // discard everything from stream
            InputStream in = telnetClient.getInputStream();
            System.out.println("Discarding stream content...");
            try {
                while (in.available() > 0) {
                    in.read();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            PrintWriter out = new PrintWriter(telnetClient.getOutputStream(), true);
            out.println(command);

            System.out.println("Reading response...");
            StringBuilder response = new StringBuilder();
            int readByte;
            try {
                while ((readByte = in.read()) != '>') {
                    response.append((char) readByte);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Response read!");

            System.out.println("--- Response:");
            System.out.println(response);
            System.out.println("---");

            responses.add(response.toString());
        }

        try {
            telnetClient.disconnect();
            System.out.println("Disconnected!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responses;
    }

    private void connect() {
        try {
            telnetClient.connect(hostname, port);
            login();
        } catch (IOException e) {
            System.err.println("Error connecting to Telnet server: " + e.getMessage());
        }
    }

    private void login() {
        PrintWriter out = new PrintWriter(telnetClient.getOutputStream(), true);
        out.println(login);
        System.out.println("Login sent!");
        InputStream in = telnetClient.getInputStream();
        try {
            while (in.read() != '>') {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Logged in!");
    }

    private void disconnect() {
        try {
            telnetClient.disconnect();
        } catch (IOException e) {
            System.err.println("Error disconnecting from Telnet server: " + e.getMessage());
        }
    }

}

