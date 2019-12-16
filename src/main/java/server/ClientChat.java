package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*-------------------------*/
/*-------SCENARIUSZ--------*/
/*-------------------------*/
/*Ta klasa to klient on sie polaczy i zacznie czatowac gdy otrzyma wiadomosc koniec zakonczy swoj zywot
 *
 * */
public class ClientChat {

    public static void main(String[] args) {
        /*ponizszy kod wrzuc w kliencie*/
        /**------------------------------------------------//
         //-------------KLIENTA TWORZENIE ------------------//
         //------------------------------------------------*/
        /**zdobadz localhost*/
        InetAddress host = null;
        /**DO SOCKETA*/
        Socket chatSocket = null;
        /**DO output*/
        ObjectOutputStream oos = null;
        /**DO input*/
        ObjectInputStream ois = null;

        /**Lapanie localHosta*/
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        /**Lacze z serwerrem*/
        /**skonfiguruj polaczenie socket do servera*/
        try {
            chatSocket = new Socket(host.getHostName(), 7777);


            /**odbierz odpowiedz serwera*/
            ois = new ObjectInputStream(chatSocket.getInputStream());
            System.out.println("Dolaczylem sb do servera");

            /**napisz do socket uzywajac ObjectOutputStream*/
            oos = new ObjectOutputStream(chatSocket.getOutputStream());
            oos.writeObject("OK");

            /**Czekam2 gracza*/
            boolean isSecond = (boolean) ois.readObject();       //tu czeka na drugiego gracza
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}


