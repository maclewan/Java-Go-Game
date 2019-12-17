package gameServerTests;

import Server.Server;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * Test na polaczenie sie 1 gracza do servera
 *Przed testem nalezy wlaczyc Server
 *
 * */
public class AllPlayersConnectionTest {
    Server s = new Server();
    boolean is2Black=false;
    boolean is1Black;

    @Test
    public void test() throws UnknownHostException {
        server a= new server();
        a.start();
        /*Gracz nr 1: */
        InetAddress host = InetAddress.getLocalHost();
        Socket socket;
        ObjectOutputStream oos;
        ObjectInputStream ois;

        try {
            //Lacze z serwerrem*/
            //skonfiguruj polaczenie socket do servera*/
            socket = new Socket(host.getHostName(), 6666);

            //odbierz odpowiedz serwera*/
            ois = new ObjectInputStream(socket.getInputStream());
            //Tutaj sprawdzam czy to byl pierwszy*/
            is1Black= (boolean) ois.readObject();

            //napisz do socket uzywajac ObjectOutputStream*/
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("OK");

            ConnectSecond();

            socket.close();
            ois.close();
            oos.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert(is1Black && !is2Black);

    }
    public void ConnectSecond() throws UnknownHostException {
        /*Gracz nr 2: */
        InetAddress host = InetAddress.getLocalHost();
        Socket socket;
        ObjectOutputStream oos;
        ObjectInputStream ois;

        try {
            //Lacze z serwerrem*/
            //skonfiguruj polaczenie socket do servera*/
            socket = new Socket(host.getHostName(), 6666);

            //odbierz odpowiedz serwera*/
            ois = new ObjectInputStream(socket.getInputStream());
            //Tutaj sprawdzam czy to byl pierwszy*/
            is2Black= (boolean) ois.readObject();

            //napisz do socket uzywajac ObjectOutputStream*/
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("OK");

            socket.close();
            ois.close();
            oos.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public class server extends Thread {
        @Override
        public synchronized void run() {

            String[] args=null;
            s.main(args);
        }
    }
}
