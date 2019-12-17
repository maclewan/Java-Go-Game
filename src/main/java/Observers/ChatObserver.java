package Observers;


import Controllers.ChatController;
import Observers.Observer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatObserver extends Thread{


    private boolean isNewMessage=false;
    private boolean isBlack;

    private Observer observer;

    private Socket chatSocket;
    private ObjectOutputStream oosChat;
    private ObjectInputStream oisChat;

    private String mesIn = new String("");
    private String mesOut = new String("");
    private String lastMes = new String("");

    private ChatController chatController;

    @Override
    public synchronized void run() {


        /**------------------------------------------------//
         //-------------KLIENTA TWORZENIE ------------------//
         //------------------------------------------------*/
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

            /**napisz do socket uzywajac ObjectOutputStream*/
            oosChat = new ObjectOutputStream(chatSocket.getOutputStream());
            oosChat.writeObject(isBlack);

            /**Czekam na 2 gracza*/
            boolean isSecondChatter = (boolean) oisChat.readObject();       //tu czeka na drugiego gracza
            boolean uselesstempBoolean = (boolean) oisChat.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        while(true){

            try {


                sleep(100);
                /**jesli jest nowa wiadomosc to ja wysylam do serwera*/
                if (isNewMessage) {
                    System.out.println("wysylam wiadomosc: " + mesOut);
                    oosChat.writeObject(mesOut);
                    isNewMessage = false;
                }

                /**pobieram wiadomosc z serwera*/
                lastMes=mesIn;
                mesIn = (String) oisChat.readObject();

                /**wypisuje wiadomosc w konsoli*/
                if(!lastMes.equals(mesIn)){

                    Platform.runLater(() ->chatController.addLabelChatText(mesIn));
                }


                /**Czy gra nie jest do wznowienia*/
                if(mesIn.equals("Bialy:\t Wznawiam gre!")||mesIn.equals("Czarny:\t Wznawiam gre!")){

                    setMesOut("");
                    System.out.println("Zamykam chat");
                    Platform.runLater(() ->chatController.closeChat());

                }
                else if(mesIn.equals("Koniec gry!")){
                    //todo: do obgadania jak to ma wygladac
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }




    public void startChat() {
        try {
            System.out.println("Starting chat!");
            Stage stage = new Stage();
            chatController = new ChatController();
            chatController.setCo(this);
            chatController.setStage(stage);


            FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("Chat.fxml"));
            loaderG.setController(chatController);

            Scene scene = new Scene(loaderG.load());
            stage.setTitle("Chat");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void setMesOut(String mesOut) {
        this.mesOut = mesOut;
        isNewMessage=true;
    }

}