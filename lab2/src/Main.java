import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * Created by andrew on 19.11.14.
 */
public class Main {
    static private InetAddress hostAddress;
    static private int port = 3000;

    private static ServerSocketChannel serverChannel;

    static private Selector selector;

    public static void main(String[] args) {

        ByteBuffer Buffer = ByteBuffer.allocate(8192);
        try {
            hostAddress = InetAddress.getByName("localhost");
            selector = initSelector();
            while (true) {
                try {
                    if (selector.select() == 0)
                        continue;

                    Iterator selectedKeys = selector.selectedKeys().iterator();
                    while (selectedKeys.hasNext()) {
                        SelectionKey key = (SelectionKey) selectedKeys.next();
                        selectedKeys.remove();

                        if (!key.isValid()) {
                            continue;
                        }

                        if (key.isAcceptable()) {
                            System.out.println(key);
                            accept(key);
                        } else if ((key.readyOps() & SelectionKey.OP_READ) ==
                                SelectionKey.OP_READ) {
                            SocketChannel sc = (SocketChannel) key.channel();
                            sc.read(Buffer);
                            for (SelectionKey ToKey : selector.keys()) {
                                if (ToKey != key) {
                                    ((SocketChannel) ToKey.channel()).write(Buffer);
                                }
                            }
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Selector initSelector() throws IOException {

        Selector socketSelector = SelectorProvider.provider().openSelector();


        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);


        InetSocketAddress isa = new InetSocketAddress(hostAddress, port);
        serverChannel.socket().bind(isa);

        serverChannel.register(socketSelector, SelectionKey.OP_ACCEPT);

        return socketSelector;
    }

    private static void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

        SocketChannel socketChannel = serverSocketChannel.accept();
        Socket socket = socketChannel.socket();
        socketChannel.configureBlocking(false);

        socketChannel.register(selector, SelectionKey.OP_READ);
    }
}
