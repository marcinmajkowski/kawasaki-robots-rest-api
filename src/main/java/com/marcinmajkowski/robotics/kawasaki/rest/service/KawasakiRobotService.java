package com.marcinmajkowski.robotics.kawasaki.rest.service;

import com.marcinmajkowski.robotics.kawasaki.client.tcp.TcpClient;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.JointDisplacement;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.Location;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.Real;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.Transformation;
import com.marcinmajkowski.robotics.kawasaki.rest.web.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class KawasakiRobotService {

    //TODO load ip from properties file
    private final TcpClient tcpClient = new TcpClient("192.168.56.1");

    public String getModel() {
        //TODO
        return "RS005L";
    }

    public Location getToolCenterPoint() {
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
        //TODO NullPointerException
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
        String response = null;
        try {
            response = tcpClient.getResponse("status");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public List<String> getAllRealNames() {
        String response = null;
        try {
            response = tcpClient.getResponse("dir /r");
        } catch (IOException e) {
            e.printStackTrace();
        }
/* Response:
dir /r
Real
 p1        p2        p3        p4        p5        p6[]

*/
        //TODO NullPointerException
        String[] tokens = response.split("\\s+");
        List<String> result = Arrays.asList(tokens).subList(tokens.length > 3 ? 3 : tokens.length, tokens.length);

        return result;
    }

    public Real getRealByName(String name) throws ResourceNotFoundException {
        String response = null;
        try {
            response = tcpClient.getResponse("list /r " + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
/* Response:
list /r k
Real
k        = 3

*/
        Real result = null;
        Matcher matcher = Pattern.compile(Pattern.quote(name) + " += +(\\d+(?:\\.\\d+)?)").matcher(response);
        if (matcher.find()) {
            result = new Real(name, Double.parseDouble(matcher.group(1)));
        } else {
            matcher = Pattern.compile("\\((P\\d+)\\)(.*?)\\r?\\n").matcher(response);
            if (!matcher.find()) {
                //TODO refactor
                throw new ResourceNotFoundException(null, null);
            }
            String code = matcher.group(1);
            String description = matcher.group(2);
            throw new ResourceNotFoundException(code, description);
        }
        return result;
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

    public List<String> getAllLocationNames() {
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

    //TODO this is temporary
    public void load(String filename) {
        try {
            tcpClient.getResponse("load " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Location getLocationByName(String name) {
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
    }

    public void deleteLocation(String name) {
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
    }
}
