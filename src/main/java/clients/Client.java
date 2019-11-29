package clients;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * Ta klasa implementuje socket Client
 *
 */
public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
        /*Tutaj wyswietlam menu*/
        /*Main main1= new Main();
        String[] arg = null;
        Main.main(arg);*/


        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        /*tutaj rozpoczynam dzialanie serwera*/
        for(int i=0; i<5;i++){
            /*skonfiguruj polaczenie socket do server*/
            socket = new Socket(host.getHostName(), 6666);

            /*napisz do socket uzywajac ObjectOutputStream*/
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Sending request to Socket Server");
            if(i==4)oos.writeObject("exit");
            else oos.writeObject(""+i);

            /*odbierz odpowiedz serwera*/
            ois = new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();
            System.out.println("Message: " + message);

            /*pozaykaj wszystko*/
            ois.close();
            oos.close();
            Thread.sleep(100);
        }
    }
}
