import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * Created by andrew on 18.11.14.
 */
public class LogParser {
    private HashMap<Integer, Integer> users;// ID, Time
    public LogParser(){
      users = new HashMap<Integer, Integer>();
    }
    public void parseLog(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String str = "";
        while(str != null) {
            str = reader.readLine();
            parseLine(str);
        }

    }

    private void parseLine(String line){
        if(line == null)
            return;
        String[] splited = line.split(", ");
        if(splited.length != 3)
            return;

        Integer last = (Integer) users.get(Integer.parseInt(splited[1]));//if an user already come here at last
        users.put(Integer.parseInt(splited[1]),Integer.parseInt(splited[0]) +(last == null? 0: last));

    }

    public TreeMap<Integer,Integer> computeUsersTimes(){
        TreeMap outA = new TreeMap<Integer,Integer>(Collections.reverseOrder());//Time, Id
        for(Map.Entry<Integer,Integer> s: users.entrySet()) {
            outA.put(s.getValue(),s.getKey());
        }

        return outA;
    }
}
