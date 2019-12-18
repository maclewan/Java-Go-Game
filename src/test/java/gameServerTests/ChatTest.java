package gameServerTests;

import Server.ChatServer;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 *
 * Test na polaczenie sie 1 gracza do servera chatu
 *
 * */
public class ChatTest { ;
    boolean isSecondChatter=false;
    @Test
    public void test() throws UnknownHostException {
        ChatServer chatServer = new ChatServer(null);
        chatServer.start();
        ConnectChatter();

    }
    public void ConnectChatter() {
        /**------------------------------------------------//
         //-------------KLIENTA 2 TWORZENIE ------------------//
         //------------------------------------------------*/
        boolean isNewMessage=false;
        boolean isBlack=false;
        Socket chatSocket;
        ObjectOutputStream oosChat;
        ObjectInputStream oisChat;

        String mesIn = new String("");
        String mesOut = new String("");
        String lastMes = new String("");
        /**zdobadz localhost*/
        InetAddress host = null;
        /**DO SOCKETA*/
        chatSocket=null ;
        /**DO output*/
        oosChat = null;
        /**DO input*/
        oisChat = null;

        /**Lapanie localHosta*/
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        /**Lacze z serwerrem czatu */
        try {
            chatSocket = new Socket(host.getHostName(), 7777);


            /**odbierz odpowiedz serwera*/
            oisChat = new ObjectInputStream(chatSocket.getInputStream());
            System.out.println("Dolaczylem sb do servera");

            /**Odpalam 2 gracza*/
            Chatter2 chat2 = new Chatter2(oisChat);
            chat2.start();

            /**napisz do socket uzywajac ObjectOutputStream*/
            oosChat = new ObjectOutputStream(chatSocket.getOutputStream());
            oosChat.writeObject(isBlack);

            /**Czekam na 2 gracza*/
            boolean isSecondChatter = (boolean) oisChat.readObject();       //tu czeka na drugiego gracza
            boolean uselesstempBoolean = (boolean) oisChat.readObject();

            oosChat.writeObject("HI");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public class Chatter2 extends Thread {
        ObjectOutputStream oosChat;
        ObjectInputStream oisChat;
        ObjectInputStream ois2;

        public Chatter2(ObjectInputStream oisChat) {
            this.ois2=oisChat;
        }

        @Override
        public synchronized void run() {
            /**------------------------------------------------//
             //-------------KLIENTA 2 TWORZENIE ------------------//
             //------------------------------------------------*/
            boolean isBlack=false;
            Socket chatSocket;

            /**zdobadz localhost*/
            InetAddress host = null;
            /**DO output*/
            oosChat = null;
            /**DO input*/
            oisChat = null;

            /**Lapanie localHosta*/
            try {
                host = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            /**Lacze z serwerrem czatu */
            try {
                chatSocket = new Socket(host.getHostName(), 7777);


                /**odbierz odpowiedz serwera*/
                oisChat = new ObjectInputStream(chatSocket.getInputStream());
                System.out.println("Dolaczylem sb do servera");

                /**napisz do socket uzywajac ObjectOutputStream*/
                oosChat = new ObjectOutputStream(chatSocket.getOutputStream());
                oosChat.writeObject(isBlack);

                /**Czekam na 2 gracza*/
                isSecondChatter = (boolean) oisChat.readObject();       //tu czeka na drugiego gracza
                boolean uselesstempBoolean = (boolean) oisChat.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            assert(isSecondChatter);

        }

    }


}
