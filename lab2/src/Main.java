import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * Created by andrew on 19.11.14.
 */
public class Main {
    static ServerSocket server;
    public static void main(String[] args){
        try {
            server = new ServerSocket(3000, 0,
                     InetAddress.getByName("localhost"));
        } catch (IOException e) {
            System.out.println(e.toString() + "\nTry again with a new hosts name");
            return;
        }
        System.out.println("Server started");
        Clients.StartToListen(server);

    }
}
