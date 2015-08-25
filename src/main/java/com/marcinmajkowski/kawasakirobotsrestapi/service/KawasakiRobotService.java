package com.marcinmajkowski.kawasakirobotsrestapi.service;

import com.marcinmajkowski.kawasakirobotsrestapi.domain.JointDisplacement;
import com.marcinmajkowski.kawasakirobotsrestapi.domain.Location;
import com.marcinmajkowski.kawasakirobotsrestapi.domain.Transformation;
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
        //TODO
        return "RS005L";
    }

    public Location getToolCenterPoint() {
        String response = kawasakiTelnetClientService.getResponse("where");
/* Response:
where
     JT1       JT2       JT3       JT4       JT5       JT6
     0.000     0.000     0.000     0.000     0.000     0.000
    X[mm]     Y[mm]     Z[mm]     O[deg]    A[deg]    T[deg]
     0.000     0.000    80.000     0.000     0.000     0.000

*/
        String[] tokens = response.split("\\s+");
        double x = Double.parseDouble(tokens[19]);
        double y = Double.parseDouble(tokens[20]);
        double z = Double.parseDouble(tokens[21]);
        double o = Double.parseDouble(tokens[22]);
        double a = Double.parseDouble(tokens[23]);
        double t = Double.parseDouble(tokens[24]);
        Transformation transformation = new Transformation(x, y, z, o, a, t);
        //TODO following will work correctly only if the robot has exactly 6 axis
        double[] joints = new double[6];
        for (int i = 0; i < joints.length; i++) {
            joints[i] = Double.parseDouble(tokens[7 + i]);
        }
        JointDisplacement jointDisplacement = new JointDisplacement(joints);
        return new Location("here", transformation, jointDisplacement);
    }

    public String getStatus() {
        return kawasakiTelnetClientService.getResponse("status");
    }

    public List<String> getAllLocationNames() {
        String response = kawasakiTelnetClientService.getResponse("dir /l");
/* Response:
dir /l
Location
 p1        p2        p3        p4        p5        p6[]

*/
        String[] tokens = response.split("\\s+");
        List<String> result = Arrays.asList(tokens).subList(tokens.length > 3 ? 3 : tokens.length, tokens.length);

        return result;
    }

    public Location getLocationByName(String name) {
        //TODO
        Transformation transformation = new Transformation(0, 0, 0, 0, 0, 0);

        return new Location(name, transformation, null);
    }
}
