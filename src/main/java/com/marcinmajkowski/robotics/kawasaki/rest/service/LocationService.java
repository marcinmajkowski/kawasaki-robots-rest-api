package com.marcinmajkowski.robotics.kawasaki.rest.service;

import com.marcinmajkowski.robotics.kawasaki.client.tcp.TcpClient;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.JointDisplacement;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.LocationVariable;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.Transformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class LocationService {
    private final TcpClient tcpClient;

    @Autowired
    public LocationService(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public LocationVariable getToolCenterPoint() {
        String response = null;
        try {
            response = tcpClient.getResponse("where");
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* Response:
        where
             JT1       JT2       JT3       JT4       JT5       JT6
             0.000     0.000     0.000     0.000     0.000     0.000
            X[mm]     Y[mm]     Z[mm]     O[deg]    A[deg]    T[deg]
             0.000     0.000    80.000     0.000     0.000     0.000

        */
        //FIXME NullPointerException
        String[] tokens = response.split("\\s+");
        double x = Double.parseDouble(tokens[19]);
        double y = Double.parseDouble(tokens[20]);
        double z = Double.parseDouble(tokens[21]);
        double o = Double.parseDouble(tokens[22]);
        double a = Double.parseDouble(tokens[23]);
        double t = Double.parseDouble(tokens[24]);
        Transformation transformation = new Transformation();
        transformation.setX(x);
        transformation.setY(y);
        transformation.setZ(z);
        transformation.setO(o);
        transformation.setA(a);
        transformation.setT(t);
        //FIXME following will work correctly only if the robot has exactly 6 axis
        double[] joints = new double[6];
        for (int i = 0; i < joints.length; i++) {
            joints[i] = Double.parseDouble(tokens[7 + i]);
        }
        JointDisplacement jointDisplacement = new JointDisplacement();
        jointDisplacement.setJoints(joints);
        LocationVariable locationVariable = new LocationVariable();
        locationVariable.setName("here");
        locationVariable.setTransformation(transformation);
        locationVariable.setJointDisplacement(jointDisplacement);
        return locationVariable;
    }

    public List<String> getAllNames() {
        String response = null;
        try {
            response = tcpClient.getResponse("dir /l");
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* Response:
        dir /l
        Location
         p1        p2        p3        p4        p5        p6[]

        */
        //TODO NullPointerException
        String[] tokens = response.split("\\s+");
        List<String> result = Arrays.asList(tokens).subList(tokens.length > 3 ? 3 : tokens.length, tokens.length);

        return result;
    }

    public LocationVariable get(String name) {
        //TODO
        String response = null;
        try {
            response = tcpClient.getResponse("list /l " + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        //TODO NullPointerException
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
        Transformation transformation = new Transformation();
        transformation.setX(x);
        transformation.setY(y);
        transformation.setZ(z);
        transformation.setO(o);
        transformation.setA(a);
        transformation.setT(t);

        LocationVariable locationVariable = new LocationVariable();
        locationVariable.setName(tokens[0]);
        locationVariable.setTransformation(transformation);
        return locationVariable;
    }

    public LocationVariable add(LocationVariable locationVariable) {
        String name = locationVariable.getName();
        LocationVariable previousValue = get(name);
        Transformation transformation = locationVariable.getTransformation();
        double x = transformation.getX();
        double y = transformation.getY();
        double z = transformation.getZ();
        double o = transformation.getO();
        double a = transformation.getA();
        double t = transformation.getT();
        String command = "point " + name + " = trans("
                + x + ", " + y + ", " + z + ", " + o + ", " + a + ", " + t + ")\r\n";
        try {
            tcpClient.getResponse(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* Whole process:
        point addme = trans(10.0, 11.0, 12.0, 13.0, 14.0, 15.0)
            X[mm]     Y[mm]     Z[mm]     O[deg]    A[deg]    T[deg]
            10.000    11.000    12.000    13.000    14.000    15.000
        Change? (If not, Press RETURN only.)


        */
        return previousValue;
    }

    public LocationVariable put(String name, Transformation transformation) {
        LocationVariable previousValue = get(name);
        //TODO
        return previousValue;
    }

    public LocationVariable put(String name, JointDisplacement jointDisplacement) {
        LocationVariable previousValue = get(name);
        StringBuilder command = new StringBuilder("point ");
        command.append(name).append(" = ").append(jointDisplacement.toInstructionParameter()).append("\r\n");
        try {
            tcpClient.getResponse(command.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return previousValue;
    }

    public LocationVariable remove(String name) {
        LocationVariable previousValue = get(name);
        //TODO prevent injecting malicious command
        String command = "delete /l " + name + "\r\n" + "1";
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
}
