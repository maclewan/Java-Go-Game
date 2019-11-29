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

        System.out.println("Stworzylem server");

        System.out.println("Czekanie na odpowiedz klienta");

        /*Tworzenie socket i czekanie na polaczenie z klientem*/
        /*Wysylam potwierdzenie do klienta1*/
        Socket socket1 = server.accept();
        //tworz ObjectOutputStream object
        ObjectOutputStream oos1 = new ObjectOutputStream(socket1.getOutputStream());
        oos1.writeObject("Jestes graczem nr 1");

        Socket socket2 = server.accept();
        /*Wysylam potwierdzenie do klienta2*/
        ObjectOutputStream oos2 = new ObjectOutputStream(socket2.getOutputStream());
        oos2.writeObject("Jestes graczem nr 2");

        while(true){



            /*czytanie z socket do ObjectInputStream object*/
            ObjectInputStream checker1 = new ObjectInputStream(socket1.getInputStream());
            ObjectInputStream checker2 = new ObjectInputStream(socket2.getInputStream());

            /*Convertowanie ObjectInputStream object na Stringa*/
            String message = (String) checker1.readObject();
            System.out.println("Dostalem widomosc: " + message);
            message = (String) checker2.readObject();
            System.out.println("Dostalem widomosc: " + message);



            //zapisz object do Socket
            oos1.writeObject("Czesc kliencie "+message);
            oos2.writeObject("Czesc kliencie "+message);


            //zamknij wszystkie zrodla
            checker1.close();
            oos1.close();
            socket1.close();

            checker2.close();
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

