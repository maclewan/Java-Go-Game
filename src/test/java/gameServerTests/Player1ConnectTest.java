package gameServerTests;

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
public class Player1ConnectTest {

    @Test
    public void test() throws UnknownHostException {
        /*Gracz nr 1: */
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        String kolor;
        String message;
        boolean isBlack=false;

        try {
            //Lacze z serwerrem*/
            //skonfiguruj polaczenie socket do servera*/
        socket = new Socket(host.getHostName(), 6666);

        //odbierz odpowiedz serwera*/
        ois = new ObjectInputStream(socket.getInputStream());
            //Tutaj sprawdzam czy to byl pierwszy*/
        isBlack= (boolean) ois.readObject();
            assert(isBlack);

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
}
