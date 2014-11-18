import java.io.IOException;
import java.util.Map;


/**
 * Created by andrew on 17.11.14.
 */
public class Main {
    public static int main(String[] args) throws IOException {
        String filename = "log.txt";
        LogParser lotParser = new LogParser();
        lotParser.parseLog(filename);//must get args[0]
        System.out.println();
        for(Map.Entry<Integer,Integer> s: lotParser.computeUsersTimes().entrySet()) {
            System.out.println("id:"+ s.getValue().toString() + " time:" + s.getKey().toString());
        }

        return 0;
    }
}
