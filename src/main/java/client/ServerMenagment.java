package client;

import Controllers.ClientController;
import javafx.scene.shape.Ellipse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerMenagment extends Thread {
    private ClientController cc;
    private int a, b;
    private boolean isInfoToSend = false; //sprawdza czy sa nowe informacje


    public ServerMenagment(ClientController cc) {
        this.cc = cc;
    }

    /*DO SOCKETA*/
    Scanner scan = new Scanner(System.in);          //scanner
    InetAddress host;  //host

    {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    Socket socket = null;                           //server
    ObjectOutputStream oos = null;                  //wyjscie
    ObjectInputStream ois = null;                   //wejscie




    /*Funkcja nasluchuje odpowiedzi od serwera i wywoluje dzialanie zaczynajace gre*/
    @Override
    public synchronized void run() {


        /**
         * Michale!
         */
        //łączę się z serwerem
        System.out.println("jestem w run");
        /*Lacze z serwerrem*/
        /*skonfiguruj polaczenie socket do servera*/
        try {
            socket = new Socket(host.getHostName(), 6666);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            System.out.println("jestem w while");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            try {
                exchangeInfo(a,b);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


            System.out.println("zara odbiere dane");
            /*odbieranie danych od serwera*/
            /*try {
                getInfoFromServer();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            System.out.println("zara wysle dane");
            /*wysylanie danych do serwera*/
           // if (isInfoToSend) {
              /*  try {
                    sendInfoToServer(a, b);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }*/
            //}
        }


    }

    public void exchangeInfo(int a, int b) throws IOException, ClassNotFoundException {

       // if(cc.madeMove) {
            /*napisz do socket uzywajac ObjectOutputStream*/
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(a);
            oos.writeObject(b);
        System.out.println("wYSYLAM " + a + b);
            cc.madeMove = false;
       // }


       // if(!cc.yourTurn){
        /*odbierz odpowiedz serwera*/
        ois = new ObjectInputStream(socket.getInputStream());
        int a1 = (int) ois.readObject();
        int b1 = (int) ois.readObject();
        System.out.println("odbieram " + a1 + b1);
        if (a1 != 20 && b1 != 20 && a1!=21 && b1!=21) {
            cc.cleanAllreadyChecked();
            Ellipse tmp = new Ellipse();


            /**
             * MACIEJU
             * na dole zakomentowalem nie udolna probe a tam nizej jest blad.
             * jak wlaczysz server i podlaczczysz obu graczy to kliknij guzik BLACK- aby otworzyc watek

             */
               /* if(cc.isBlack)              tmp.setFill(Color.WHITE);
                else             tmp.setFill(Color.BLACK);
                cc.checkers[a1][b1]=tmp;
            cc.board.getChildren().add(cc.checkers[a1][b1]);*/
            /*cc.isBlack=!cc.isBlack;
            cc.addChecker(a1,b1);
            cc.isBlack=!cc.isBlack;*/
            if(cc.checkers[a1][b1]!=null) {
                cc.isBlack = !cc.isBlack;
                System.out.println("dodaje: piona " + a1 + b1);
                cc.addChecker(a1, b1,!cc.isBlack);
                cc.groupCheckers();
                cc.killer();
                cc.isBlack = !cc.isBlack;
            }
            else         System.out.println("jestem w else");
            }

        //}

        //  firstTime=false;
        //}



        //socket.close();
      //  ois.close();
       // oos.close();


        cc.yourTurn=true;


    }




    public ClientController getCc() {
        return cc;
    }

    public void setCc(ClientController cc) {
        this.cc = cc;
    }


    public void setInfo(boolean info) {
        isInfoToSend = info;
    }

    public void setAB(int a, int b){
        this.a=a;
        this.b=b;
        isInfoToSend=true;

    }
}