import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by andrew on 19.11.14.
 */
public class Clients extends Thread{
    private static LinkedList<Clients> clients = new LinkedList<Clients>();
    private static ServerSocket server;
    private Socket currentSocket;
    int ID;
    Clients(int ID){
        this.ID = ID;
        start();
    }

    public static void StartToListen(ServerSocket Server){
        server = Server;
        clients.add(new Clients(1));
    }

    public void run(){
        try {
            currentSocket = server.accept();
            clients.add(new Clients(ID + 1));
        } catch (IOException e) {
            System.out.println("error in connect id: " + ID);
            run();
            return;
        }

        while(true){
            try {
                InputStream is = currentSocket.getInputStream();
                byte buf[] = new byte[64*1024];
                int r = is.read(buf);
                String data = new String(buf, 0, r);
                WriteToAll(data,ID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static synchronized void WriteToAll(String data, int notInID){

        for(Clients i:clients) {
            if(i.currentSocket!= null) {
                System.out.println("Client ID: " + i.ID + " was disconected");
                clients.remove(i);
            }
        }
        for(Clients i:clients)
            try {
                if(i.ID != notInID && i.currentSocket!= null) {
                    i.currentSocket.getOutputStream().write(data.getBytes());
                }
            } catch (IOException e) {
                System.out.println("Can't write in id: " + i.ID + "\nDisconnected ");
            }
    }

}
