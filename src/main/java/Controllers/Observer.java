package Controllers;

import javafx.application.Platform;
import javafx.scene.shape.Ellipse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Observer extends Thread{
    WaitingFrameController wfc;
    boolean isServer=true;
    /*DO SOCKETA*/
    Scanner scan = new Scanner(System.in);
    //zdobadz localhost
    InetAddress host;
    Socket socket = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    ObjectOutputStream oosa = null;
    ObjectInputStream oisa = null;
    ClientController cc;
        int a=21;
        int b=21;
        int lastA=21;
        int lastB=21;
        boolean startTalk=false;

    public Observer(WaitingFrameController wfc){
        this.wfc=wfc;
    }

    /*Funkcja nasluchuje odpowiedzi od serwera i wywoluje dzialanie zaczynajace gre*/
    @Override
    public synchronized void run() {
        try {


            /**
             *
             * Etap laczenia sie 2 klientow
             *
             * */


            ClientController cc = new ClientController();
            host = InetAddress.getLocalHost();

            /*Lacze z serwerrem*/
            /*skonfiguruj polaczenie socket do servera*/
            socket = new Socket(host.getHostName(), 6666);

            /*odbierz odpowiedz serwera*/
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("Dolaczylem sb do servera");

            /*Tutaj sprawdzam czy to byl pierwszy*/
            cc.isBlack = (boolean) ois.readObject();
            if (cc.isBlack) System.out.println("Jestem czarny");
            else {
                System.out.println("Jestem bialy");
                cc.yourTurn = false;
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
                wfc.startGame(cc);
                isServer=true;
                return;
            });

            /**
             *
             * Etap gry miedzy 2 klientami
             *
             * */

            if(isSecond) {
                while (!startTalk) {
                    a=cc.a;
                    b=cc.b;
                    //System.out.println("jestem w while");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    try {
                        /*napisz do socket uzywajac ObjectOutputStream*/
                        //oosa = new ObjectOutputStream(socket.getOutputStream());
                            oos.writeObject(a);
                            oos.writeObject(b);
                        //System.out.println("wYSYLAM " + a + b);
                        cc.madeMove = false;

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
                                Ellipse tmp = new Ellipse();
                                if (cc.checkers[a1][b1] == null) {
                                    cc.isBlack = !cc.isBlack;
                                    System.out.println("dodaje: piona " + a1 + b1);
                                    Platform.runLater(() -> cc.addChecker(a1, b1));
                                    cc.groupCheckers();
                                    Platform.runLater(() -> cc.killer());
                                    cc.isBlack = !cc.isBlack;
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
            if(startTalk)
            {
                //******************************//
                //---- UNDER CONSTRUCKTION------//
                //******************************//

                /*Tutej bedzie rozmowa sie odbywac*/
            }





            //socket.close();
            //ois.close();
            //oos.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Platform.runLater(() ->  {
                isServer=false;
                wfc.absentServer();           /*wypisuje brak serwera*/

            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try{
            Thread.sleep(3000);        /*odczekuje 3 sekundy i wraca do menu*/
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        if(!isServer)                     /*tylko gdy nie ma serwera*/
            Platform.runLater(() ->  {
                wfc.backToMenu();
                return;
            });



    }

    public void exchangeInfo(int a, int b) throws IOException, ClassNotFoundException {

        // if(cc.madeMove) {

    }
    public void setAB(int a, int b){
        this.a=a;
        this.b=b;
    }


}
