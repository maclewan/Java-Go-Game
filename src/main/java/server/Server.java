package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * Ta klasa implementuje Socket server
 *
 */


public class Server {
    private static ServerSocket server;
    /*port socket serer-a*/
    private static int port = 6666;



    public static void main() throws IOException, ClassNotFoundException{
        /*tworzenie socket serwer*/
        server = new ServerSocket(port);

        while(true){

            System.out.println("Czekanie na odpowiedz klienta");

            /*Tworzenie socket i czekanie na polaczenie z klientem*/
            Socket socket1 = server.accept();
            Socket socket2 = server.accept();

            /*czytanie z socket do ObjectInputStream object*/
            ObjectInputStream ois1 = new ObjectInputStream(socket1.getInputStream());
            ObjectInputStream ois2 = new ObjectInputStream(socket2.getInputStream());

            /*Convertowanie ObjectInputStream object na Stringa*/
            String message = (String) ois1.readObject();
            System.out.println("Dostalem widomosc: " + message);
            message = (String) ois2.readObject();
            System.out.println("Dostalem widomosc: " + message);

            //tworz ObjectOutputStream object
            ObjectOutputStream oos1 = new ObjectOutputStream(socket1.getOutputStream());
            ObjectOutputStream oos2 = new ObjectOutputStream(socket2.getOutputStream());

            //zapisz object do Socket
            oos1.writeObject("Czesc kliencie "+message);
            oos2.writeObject("Czesc kliencie "+message);


            //zamknij wszystkie zrodla
            ois1.close();
            oos1.close();
            socket1.close();

            ois2.close();
            oos2.close();
            socket2.close();

            //terminate the server jesli client wysle zapytanie wyjscia
            if(message.equalsIgnoreCase("exit")) break;
        }
        System.out.println("Shutting down Socket server!!");
        //zamknij ServerSocket object
        server.close();
    }

}

