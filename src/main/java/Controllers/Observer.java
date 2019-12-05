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
            //zdobadz localhost
            InetAddress host;


            host = InetAddress.getLocalHost();

            Socket socket = null;
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;
            String myMessage;
            String message;


            ClientController clientController = new ClientController();



            //******************************//
            //---- UNDER CONSTRUCKTION------//
            //******************************//

            /*Lacze z serwerrem*/
            /*skonfiguruj polaczenie socket do servera*/
            socket = new Socket(host.getHostName(), 6666);

            /*odbierz odpowiedz serwera*/
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("Dolaczylem sb do servera");

            /*Tutaj sprawdzam czy to byl pierwszy*/
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
            boolean isSecond = (boolean) ois.readObject();       //tu czeka

            Thread.sleep(1000);



            /*zacznij gre - otworz gui*/
            Platform.runLater(() ->  {
                wfc.startGame(clientController);
                return;
            });


            socket.close();
            ois.close();
            oos.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Platform.runLater(() ->  {
                wfc.absentServer();           /*wypisuje brak serwera*/

            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

      /*  try{
            Thread.sleep(3000);        /*odczekuje 3 sekundy i wraca do menu*/
        /*}
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }*/
        /**********************/
        /* MACIEJU*/
        /*musze to zakomentowac nizej i wyzej bo po 3 sek bezruchu gra sie wylacza xD*/
        /************************/
        /*Platform.runLater(() ->  {
            wfc.backToMenu();
            return;
        });*/



    }
}
