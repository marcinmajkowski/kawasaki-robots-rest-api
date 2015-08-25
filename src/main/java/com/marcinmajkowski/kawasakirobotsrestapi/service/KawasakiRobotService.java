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
        String response = kawasakiTelnetClientService.getResponse("list /l " + name);
/* Response (location exists):
list /l p1
Location
p1           0.000     0.000    80.000     0.000     0.000     0.000

*/
/* Response (location does not exist):
list /l p12
p12:Variable (or program) does not exist.
(P0152)Undefined variable (or program).

*/
        //TODO corner cases
        //TODO arrays
        String lines[] = response.split("\\r?\\n");
        String[] tokens = null;
        // find the line that starts with name
        for (int i = 2; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith(name.toLowerCase())) {
                tokens = line.split("\\s+");
                break;
            }
        }
        if (tokens == null) {
            return null;
        }
        double x = Double.parseDouble(tokens[1]);
        double y = Double.parseDouble(tokens[2]);
        double z = Double.parseDouble(tokens[3]);
        double o = Double.parseDouble(tokens[4]);
        double a = Double.parseDouble(tokens[5]);
        double t = Double.parseDouble(tokens[6]);
        Transformation transformation = new Transformation(x, y, z, o, a, t);

        return new Location(tokens[0], transformation, null);
    }

    public void addLocation(Location location) {
        Transformation transformation = location.getTransformation();
        String name = location.getName();
        double x = transformation.getX();
        double y = transformation.getY();
        double z = transformation.getZ();
        double o = transformation.getO();
        double a = transformation.getA();
        double t = transformation.getT();
        String command = "point " + name + " = trans("
                + x + ", " + y + ", " + z + ", " + o + ", " + a + ", " + t + ")\r\n";
        kawasakiTelnetClientService.getResponse(command);
/* Whole process:
point addme = trans(10.0, 11.0, 12.0, 13.0, 14.0, 15.0)
    X[mm]     Y[mm]     Z[mm]     O[deg]    A[deg]    T[deg]
    10.000    11.000    12.000    13.000    14.000    15.000
Change? (If not, Press RETURN only.)


*/
    }
}
