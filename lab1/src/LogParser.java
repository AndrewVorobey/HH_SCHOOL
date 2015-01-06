import java.io.*;
import java.util.*;


/**
 * Created by andrew on 18.11.14.
 */
public class LogParser {
    private HashMap<Integer, Integer> users;// ID, Time

    public LogParser() {
        users = new HashMap<Integer, Integer>();
    }

    public void parseLog(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filename)));
        String str;
        while ((str = br.readLine()) != null)
            parseLine(str);
        br.close();
    }

    private void parseLine(String line) {
        if (line == null)
            return;
        String[] splited = line.split(", ");
        if (splited.length != 3)
            return;
        if (!splited[2].equals("login") && !splited[2].equals("logout"))
            return;

        Integer last = users.get(Integer.parseInt(splited[1]));//if an user already come here at last
        users.put(Integer.parseInt(splited[1]), Integer.parseInt(splited[0]) * (splited.equals("login") ? -1 : 1) + (last == null ? 0 : last));

    }

    public TreeMap<Integer, Integer> computeUsersTimes() {
        TreeMap outA = new TreeMap<Integer, Integer>(Collections.reverseOrder());//Time, Id
        for (Map.Entry<Integer, Integer> s : users.entrySet()) {
            if (s.getValue() > 0)
                outA.put(s.getValue(), s.getKey());
        }

        return outA;
    }
}
