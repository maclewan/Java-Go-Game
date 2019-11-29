package clients;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * Ta klasa implementuje socket Client
 *
 */
public class ClientPlayer {

    String myMessage;
    public static void main() throws IOException, ClassNotFoundException, InterruptedException{
        Scanner scan = new Scanner(System.in);
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        String myMessage;
        String message;
        /*tutaj rozpoczynam dzialanie serwera*/
        while(true){
            /*skonfiguruj polaczenie socket do servera*/
            socket = new Socket(host.getHostName(), 6666);

            /*napisz do socket uzywajac ObjectOutputStream*/
            myMessage  = scan.nextLine();
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(myMessage);

            /*odbierz odpowiedz serwera*/
            ois = new ObjectInputStream(socket.getInputStream());
            message= (String) ois.readObject();
            System.out.println("Message: " + message);



            /*pozamykaj wszystko*/
            ois.close();
            oos.close();
            Thread.sleep(100);
        }
    }
}
