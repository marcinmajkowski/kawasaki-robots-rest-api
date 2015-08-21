package com.marcinmajkowski.kawasakirobotsrestapi.service;

import org.apache.commons.net.telnet.InvalidTelnetOptionException;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TelnetNotificationHandler;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@Service
public class KawasakiTelnetClientService {

    private static final String HOSTNAME = "localhost";
    private static final int PORT = 9105;
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
    }

    public synchronized String query(String command) {
        if (!telnetClient.isConnected()) {
            connect();
        }

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
        String response = "";
        int readByte;
        try {
            while ((readByte = in.read()) != '>') {
                response = response + (char) readByte;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Response read!");

        try {
            telnetClient.disconnect();
            System.out.println("Disconnected!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("--- Response:");
        System.out.println(response);
        System.out.println("---");

        return response;
    }

    private void connect() {
        try {
            telnetClient.connect(HOSTNAME, PORT);
            login();
        } catch (IOException e) {
            System.err.println("Error connecting to Telnet server: " + e.getMessage());
        }
    }

    private void login() {
        PrintWriter out = new PrintWriter(telnetClient.getOutputStream(), true);
        out.println("as");
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

