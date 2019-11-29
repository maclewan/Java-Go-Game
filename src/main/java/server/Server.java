package Server;

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

    public static void main(String args[]) throws IOException, ClassNotFoundException{
        /*tworzenie socket serwer*/
        server = new ServerSocket(port);

        while(true){
            System.out.println("Czekanie na odpowiedz klienta");

            /*Tworzenie socket i czekanie na polaczenie z klientem*/
            Socket socket = server.accept();

            /*czytanie z socket do ObjectInputStream object*/
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            /*Convertowanie ObjectInputStream object na Stringa*/
            String message = (String) ois.readObject();
            System.out.println("Dostalem widomosc: " + message);

            //tworz ObjectOutputStream object
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            //zapisz object do Socket
            oos.writeObject("Czesc kliencie "+message);

            //zamknij wszystkie zrodla
            ois.close();
            oos.close();
            socket.close();

            //terminate the server jesli client wysle zapytanie wyjscia
            if(message.equalsIgnoreCase("exit")) break;
        }
        System.out.println("Shutting down Socket server!!");
        //zamknij ServerSocket object
        server.close();
    }

}

