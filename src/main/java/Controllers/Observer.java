package Controllers;

import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**Klasa Observer odpowiedzialna za polaczenie z serverem*/
public class Observer extends Thread{
    WaitingFrameController wfc;
    ClientController cc;
    private boolean endGame=false;
    /**Scanner do wymiany informacji po tym jak obaj gracze zdecyduja na zakonczenie gry*/
    Scanner scanner = new Scanner(System.in);

    private boolean isServer=true;
    private boolean isChatOpen=false;
    /**zdobadz localhost*/
    InetAddress host;
    /**DO SOCKETA*/
    Socket socket = null;
    /**DO output*/
    ObjectOutputStream oos = null;
    /**DO input*/
    ObjectInputStream ois = null;
    //ObjectOutputStream oosa = null;//TO1
    //ObjectInputStream oisa = null;//TO1
    //ClientController cc;//TO1
    /**Pozycja pionka do dodania*/
        int a=21;
    /**Pozycja pionka do dodania*/
        int b=21;
    /**Pozycja ostatniego dodanego pionka*/
        int lastA=21;
    /**Pozycja ostatniego dodanego pionka*/
        int lastB=21;
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
            cc.isBlack = (boolean) ois.readObject();
            if (cc.isBlack) {
                System.out.println("Jestes czarny");
            }
            else {
                System.out.println("Jestes bialy");
                cc.yourTurn = false;
            }



            /**napisz do socket uzywajac ObjectOutputStream*/
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("OK");



            /**Odbierz wiadomosc nt 2 gracza*/
            /**odbierz odpowiedz serwera*/
            boolean isSecond = (boolean) ois.readObject();       //tu czeka

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

            while (true) {
                if (isSecond&&!isChatOpen) {
                    /**Otwieramy wymiane wiadomości do obslugi rozgrywki*/
                    runGame();
                }
                /**Zaczynamy chat*/
                if(!isChatOpen) {
                    isChatOpen=true;
                    Platform.runLater(() -> {
                        runChat();
                    });
                }

                if(endGame){
                    //todo: wyslij do serwera informacje o checi zakonczenia gry przez
                }

            }




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
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        if(!isServer)                     /**tylko gdy nie ma serwera*/
            Platform.runLater(() ->  {
                wfc.backToMenu();
                return;
            });

    }



    public void runGame(){
        while (!startTalk) {
            a=cc.tempPoint.getX();
            b=cc.tempPoint.getY();
            //System.out.println("jestem w while");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            try {
                /**napisz do socket uzywajac ObjectOutputStream*/
                //oosa = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(a);
                oos.writeObject(b);
                //System.out.println("wYSYLAM " + a + b);
                cc.madeMove = false;

                /**odbierz od socket uzywajac ObjectInputStream*/
                int a1 = (int) ois.readObject();
                int b1 = (int) ois.readObject();
                if (a1 == 20 && b1 == 20 && a == 20 && b == 20) {
                    System.out.println("Rozpoczynam czat");
                    startTalk = true;
                    break;
                }
                //System.out.println("odbieram " + a1 + b1);
                if(a1==20 && b1==20) cc.yourTurn=true;
                if(lastA!=a1 || lastB!=b1) {
                    lastA=a1;
                    lastB=b1;
                    cc.yourTurn = true;
                    if (a1 >= 0 && a1 < 20 && b1 >= 0 && b1 < 20) {//ten war. to dod. zabezpieczenie w
                        cc.cleanAllreadyChecked();
                        if (cc.checkers[a1][b1] == null) {
                            System.out.println("dodaje: piona " + a1 + b1);

                            Platform.runLater(() -> cc.updateTempPoint(a1, b1,!cc.isBlack));
                            Platform.runLater(() -> cc.addChecker(cc.getTempPoint()));

                            Platform.runLater(() -> cc.killer());
                        }
                        // else         System.out.println("jestem w else");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


            //System.out.println("zara odbiere dane");
        }

    }

    public void runChat(){

        ChatObserver chatObserver = new ChatObserver(socket,oos,ois,this);
        chatObserver.start();
    }

    public void setEndGame(boolean endGame){
        this.endGame=endGame;
        isChatOpen=false;

    }

    public void continueGame(){
        isChatOpen=false;
        //todo: wyslij info do serwera że pora wrocic do gry
    }


}
