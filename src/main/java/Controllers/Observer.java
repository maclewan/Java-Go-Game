package Controllers;

import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Observer extends Thread{
    WaitingFrameController wfc;


    public Observer(WaitingFrameController wfc){
        this.wfc=wfc;
    }

    /*Funkcja nasluchuje odpowiedzi od serwera i wywoluje dzialanie zaczynajace gre*/
    @Override
    public synchronized void run() {
        try {





            /*DO SOCKETA*/
            Scanner scan = new Scanner(System.in);
            //get the localhost IP address, if server is running on some other IP, you need to use that
            InetAddress host;


            host = InetAddress.getLocalHost();

            Socket socket = null;
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;
            String myMessage;
            String message;


            ClientController clientController = new ClientController();


            /*Tutaj sprawdzam czy to byl pierwszy*/
            System.out.println("Dolaczylem sb do servera");
            //******************************//
            //---- UNDER CONSTRUCKTION------//
            //******************************//

            /*Lacze z serwerrem*/
            /*skonfiguruj polaczenie socket do servera*/
            socket = new Socket(host.getHostName(), 6666);

            /*odbierz odpowiedz serwera*/
            ois = new ObjectInputStream(socket.getInputStream());
            clientController.isBlack = (boolean) ois.readObject();
            if (clientController.isBlack) System.out.println("Jestem czarny");
            else {
                System.out.println("Jestem bialy");
                clientController.yourTurn = false;
            }


            /*napisz do socket uzywajac ObjectOutputStream*/
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("OK");



            /*Odbierz wiadomosc nt 2 gracza*/
            /*odbierz odpowiedz serwera*/
            boolean isSecond = (boolean) ois.readObject();       //tu sie wiesza

            /*zacznij gre - otworz gui*/
            Platform.runLater(() ->  {
                wfc.startGame(clientController);
            });




            socket.close();
            ois.close();
            oos.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
