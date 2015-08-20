package com.marcinmajkowski.kawasakirobotsrestapi.service;

import org.apache.commons.net.telnet.TelnetNotificationHandler;

/**
 * Created by Marcin on 2015-08-20.
 */
public class KawasakiTelnetNotificationHandler implements TelnetNotificationHandler {
    public void receivedNegotiation(int negotiation_code, int option_code) {
        String command = null;
        if (negotiation_code == TelnetNotificationHandler.RECEIVED_DO) {
            command = "DO";
        } else if (negotiation_code == TelnetNotificationHandler.RECEIVED_DONT) {
            command = "DONT";
        } else if (negotiation_code == TelnetNotificationHandler.RECEIVED_WILL) {
            command = "WILL";
        } else if (negotiation_code == TelnetNotificationHandler.RECEIVED_WONT) {
            command = "WONT";
        }
        System.out.println("Received " + command + " for option code " + option_code);
    }

}
