package com.marcinmajkowski.kawasakirobotsrestapi.service;

import com.marcinmajkowski.kawasakirobotsrestapi.domain.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Marcin on 2015-08-20.
 */
@Service
public class KawasakiRobotService {

    private final KawasakiTelnetClientService kawasakiTelnetClientService;

    @Autowired
    public KawasakiRobotService(KawasakiTelnetClientService kawasakiTelnetClientService) {
        this.kawasakiTelnetClientService = kawasakiTelnetClientService;
    }

    public String getModel() {
        return "RS005L";
    }

    public Location getToolCenterPoint() {
        String response = kawasakiTelnetClientService.query("where");
        List<String> tokens = Arrays.asList(response.split("\\s+"));
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println(i + " " + tokens.get(i));
        }
        double x = Double.parseDouble(tokens.get(19));
        double y = Double.parseDouble(tokens.get(20));
        double z = Double.parseDouble(tokens.get(21));
        double o = Double.parseDouble(tokens.get(22));
        double a = Double.parseDouble(tokens.get(23));
        double t = Double.parseDouble(tokens.get(24));
        double[] joints = new double[6];
        for (int i = 0; i < joints.length; i++) {
            joints[i] = Double.parseDouble(tokens.get(7 + i));
        }
        return new Location("TCP", x, y, z, o, a, t, joints);
    }

    public String getStatus() {
        return kawasakiTelnetClientService.query("status");
    }

}
