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
/* Response:
where
     JT1       JT2       JT3       JT4       JT5       JT6
     0.000     0.000     0.000     0.000     0.000     0.000
    X[mm]     Y[mm]     Z[mm]     O[deg]    A[deg]    T[deg]
     0.000     0.000    80.000     0.000     0.000     0.000

*/
        //TODO following will work only if the robot has exactly 6 axis
        String[] tokens = response.split("\\s+");
        double x = Double.parseDouble(tokens[19]);
        double y = Double.parseDouble(tokens[20]);
        double z = Double.parseDouble(tokens[21]);
        double o = Double.parseDouble(tokens[22]);
        double a = Double.parseDouble(tokens[23]);
        double t = Double.parseDouble(tokens[24]);
        double[] joints = new double[6];
        for (int i = 0; i < joints.length; i++) {
            joints[i] = Double.parseDouble(tokens[7 + i]);
        }
        return new Location("TCP", x, y, z, o, a, t, joints);
    }

    public String getStatus() {
        return kawasakiTelnetClientService.query("status");
    }

}
