package Controllers;

import client.Point;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**Klasa Observer odpowiedzialna za polaczenie z serverem*/
public class Observer extends Thread{

    WaitingFrameController wfc;
    ClientController cc;
    private boolean endGame=false;
    /**Scanner do wymiany informacji po tym jak obaj gracze zdecyduja na zakonczenie gry*/

    private boolean continueGames=true;

    private boolean isServer=true;
    private ChatObserver chatObserver;
    private boolean isChatOpen=false;
    /**zdobadz localhost*/
    InetAddress host;
    /**DO SOCKETA*/
    Socket socket = null;
    /**DO output*/
    ObjectOutputStream oos = null;
    /**DO input*/
    ObjectInputStream ois = null;

    /** Lista pionków do dodania*/
    ArrayList<Point> pointsList = new ArrayList<>();

    /** Ostatnia lista pionków*/
    ArrayList<Point> lastPointsList = new ArrayList<>();

    /**Pionek do dodania*/
        Point a = new Point(21,21);



    /**Czy ma sie zaczac rozmowa*/
        boolean startTalk=false;

    public Observer(WaitingFrameController wfc){
        this.wfc=wfc;
    }

    /**Funkcja nasluchuje odpowiedzi od serwera i wywoluje dzialanie zaczynajace gre*/
    @Override
    public synchronized void run() {
        try {


            /**
             *
             * Etap laczenia sie 2 klientow
             *
             * */


            cc = new ClientController();
            host = InetAddress.getLocalHost();

            /**Lacze z serwerrem*/
            /**skonfiguruj polaczenie socket do servera*/
            socket = new Socket(host.getHostName(), 6666);

            /**odbierz odpowiedz serwera*/
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("Dolaczylem sb do servera");

            /**Tutaj sprawdzam czy to byl pierwszy*/
            cc.setIsBlack((boolean) ois.readObject());
            if (cc.isBlack()) {
                System.out.println("Jestes czarny");

            }
            else {
                System.out.println("Jestes bialy");
            }


            /**napisz do socket uzywajac ObjectOutputStream*/
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("OK");


            /**Odbierz wiadomosc nt 2 gracza*/
            /**odbierz odpowiedz serwera*/
            boolean isSecond = (boolean) ois.readObject();       //tu czeka na drugiego gracza

            if(isSecond){
                Thread.sleep(500);
            }

            chatObserver = new ChatObserver();
            chatObserver.start();

            Thread.sleep(1000);

            /**zacznij gre - otworz gui*/
            Platform.runLater(() ->  {
                wfc.startGame(cc);
                isServer=true;
                return;
            });



            /**
             *
             * Etap gry miedzy 2 klientami
             *
             * */


            System.out.println("\nWszedlem do gry");
            /**Otwieramy wymiane wiadomości do obslugi rozgrywki*/
            sleep(1000); //czekam az sie watki rozpoczna
            runGame();



        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Platform.runLater(() ->  {
                isServer=false;
                wfc.absentServer();           /**wypisuje brak serwera*/

            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try{
            Thread.sleep(3000);        /**odczekuje 3 sekundy i wraca do menu*/
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        if(!isServer)                     /**tylko gdy nie ma serwera*/
            Platform.runLater(() ->  {
                wfc.backToMenu();
                return;
            });

    }



    public void runGame(){
        startTalk=!true;
        while (true) {
            a=cc.getPointToPush();

            try {
                /**napisz do socket uzywajac ObjectOutputStream*/
                if(cc.isSthToPush()) {

                    oos.writeObject(a);

                    cc.setSthToPush(false);
                    System.out.println("Wysylam zapytanie o punkt " + a.getX() +";"+ a.getY());
                }


                /**odbierz od socket uzywajac ObjectInputStream*/
                pointsList=new ArrayList<>();
                pointsList = (ArrayList<Point>) ois.readObject();


                if(pointsList.size()>0&&pointsList.get(0).getX()>20){
                    checkSpecialSigns(pointsList.get(0).getX());
                }
                else if(!equalsArrayLists(lastPointsList,pointsList)) {
                    lastPointsList=pointsList;

                    cc.setArrayOfPoints(pointsList);
                    Platform.runLater(() ->cc.addArrayOfPoints());

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    private void checkSpecialSigns(int x) {
        if(x==69) {
            Platform.runLater(() ->chatObserver.startChat());

        }
    }




    public boolean equalsArrayLists(ArrayList<Point> a, ArrayList<Point> b){
        if(a.size()!=b.size()){
            return false;
        }
        for(int i=0;i<a.size();i++){
            if(a.get(i).getX()!=b.get(i).getX()||a.get(i).getY()!=b.get(i).getY())
                return false;
        }
        return true;
    }


}
