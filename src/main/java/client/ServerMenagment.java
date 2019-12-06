package client;

import Controllers.ClientController;
import javafx.scene.paint.Color;
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
        //if(!isBlack && firstTime) {

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



    private void getInfoFromServer() throws IOException, ClassNotFoundException {
        System.out.println("jestem w getinfo");
        /*Lacze z serwerrem*/
        /*skonfiguruj polaczenie socket do servera*/
        socket = new Socket(host.getHostName(), 6666);


        /*odbierz odpowiedz serwera*/
        ois = new ObjectInputStream(socket.getInputStream());
        int a1 = (int) ois.readObject();
        int b1 = (int) ois.readObject();
        if (a1 != 20 && b1 != 20) {
            cc.cleanAllreadyChecked();
                /*if (checkers[a1][b1] != null) {
                    removeChecker(a1,b1);   //tutaj usuwanie swoich wlasnych pionkow
                }
                else {*/
            cc.isBlack = !cc.isBlack;
            cc.addChecker(a1, b1);
            cc.groupCheckers();
            cc.killer();
            cc.isBlack = !cc.isBlack;
            //}

        }
     /*   else if(cc.lastPass)
        {
            cc.startChat();
        }*/

        cc.yourTurn=true;
        ois.close();
        socket.close();

    }
    private void sendInfoToServer(int a, int b) throws IOException, ClassNotFoundException {
        System.out.println("jestem w sendinfo");
        /*Lacze z serwerrem*/
        /*skonfiguruj polaczenie socket do servera*/
        socket = new Socket(host.getHostName(), 6666);

        /*napisz do socket uzywajac ObjectOutputStream*/
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(a);
        oos.writeObject(b);
        //  firstTime=false;
        //}


        oos.close();
        socket.close();


    }

    public void exchangeInfo(int a, int b) throws IOException, ClassNotFoundException {
        /*Lacze z serwerrem*/
        /*skonfiguruj polaczenie socket do servera*/
        socket = new Socket(host.getHostName(), 6666);

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
        if (a1 != 20 && b1 != 20) {
            cc.cleanAllreadyChecked();
                /*if (checkers[a1][b1] != null) {
                    removeChecker(a1,b1);   //tutaj usuwanie swoich wlasnych pionkow
                }
                else {*/
            Ellipse tmp = new Ellipse();


                if(cc.isBlack)              tmp.setFill(Color.WHITE);
                else             tmp.setFill(Color.BLACK);
                cc.checkers[a1][b1]=tmp;
            cc.board.getChildren().add(cc.checkers[a1][b1]);
            /*if(cc.checkers[a1][b1]!=null) {
                cc.isBlack = !cc.isBlack;
                System.out.println("dodaje: piona " + a1 + b1);
                cc.addChecker(a1, b1);
                cc.groupCheckers();
                cc.killer();
                cc.isBlack = !cc.isBlack;
            }*/
            //else         System.out.println("jestem w else");
            }

        //}

        //  firstTime=false;
        //}



        socket.close();
        ois.close();
        oos.close();


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