import java.io.IOException;


/**
 * Created by andrew on 17.11.14.
 */
public class Main {
    public static int main(String[] args) throws IOException {
        String filename = "log.txt";
        LogParser Analizator = new LogParser();
        Analizator.initializeFromFiles(filename);//must get args[0]
        System.out.println(Analizator.computeUsersTimes());

        return 0;
    }
}
