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
        //todo:
        /*ponizszy kod wrzuc w kliencie*/
        /**------------------------------------------------//
         //-------------KLIENTA TWORZENIE ------------------//
         //------------------------------------------------*/
        /**zdobadz localhost*/
        InetAddress host = null;    //host jest ten sam co od gameserver - nie musisz tego deklarowac
        /**DO SOCKETA*/
        Socket chatSocket ;
        /**DO output*/
        ObjectOutputStream oosChat;
        /**DO input*/
        ObjectInputStream oisChat;

        /**Lapanie localHosta*/                 //host jest ten sam co od gameserver - nie musisz tego deklarowac
        try {                                   //host jest ten sam co od gameserver - nie musisz tego deklarowac
            host = InetAddress.getLocalHost();  //host jest ten sam co od gameserver - nie musisz tego deklarowac
        } catch (UnknownHostException e) {      //host jest ten sam co od gameserver - nie musisz tego deklarowac
            e.printStackTrace();                //host jest ten sam co od gameserver - nie musisz tego deklarowac
        }                                       //host jest ten sam co od gameserver - nie musisz tego deklarowac

        /**Lacze z serwerrem czatu */
        try {
            chatSocket = new Socket(host.getHostName(), 7777);


            /**odbierz odpowiedz serwera*/
            oisChat = new ObjectInputStream(chatSocket.getInputStream());
            System.out.println("Dolaczylem sb do servera");

            /**napisz do socket uzywajac ObjectOutputStream*/
            oosChat = new ObjectOutputStream(chatSocket.getOutputStream());
            oosChat.writeObject("OK");

            /**Czekam na 2 gracza*/
            boolean isSecondChatter = (boolean) oisChat.readObject();       //tu czeka na drugiego gracza
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*W TYM MOMENCIE OBAJ GRACZE SĄ PODLACZENI ale wysylac
        wiadomosci miedzy soba beda mogli dopiero
        gdy server otworzy drzwi (zobacz klasa wywolajChat)*/

        //PISANIE WIADOMOSCI: oosChat.writeObject(mesOut);
        //CZYTANIE WIADOMOSCI: oisChat.readObject(mesIn);
        //Wiadomosc:  "Wznawiam gre!" konczy czat i wznawia server(6666) nasluchujacy ruchy gracza

    }

}


