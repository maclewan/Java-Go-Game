package client;

import Controllers.ClientController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerMenagment extends Thread{
    private ClientController cc;

private boolean isInfo=false; //sprawdza czy sa nowe informacje

    public ServerMenagment(ClientController cc){
        this.cc=cc;
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

        while(true)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

}
    public void exchangeInfo(int a, int b) throws IOException, ClassNotFoundException {


        /*Lacze z serwerrem*/
        /*skonfiguruj polaczenie socket do servera*/
        socket = new Socket(host.getHostName(), 6666);

        //if(!isBlack && firstTime) {
        /*napisz do socket uzywajac ObjectOutputStream*/
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(a);
        oos.writeObject(b);
        //  firstTime=false;
        //}
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
        else if(cc.lastPass)
        {
            cc.startChat();
        }

        cc.yourTurn=true;

        //robie ruch
        /*cleanAllreadyChecked();
        addChecker(a, b);
        System.out.println("Robie se rucha");*/

        socket.close();
        ois.close();
        oos.close();
    }

    public ClientController getCc() {
        return cc;
    }

    public void setCc(ClientController cc) {
        this.cc = cc;
    }

    public boolean isInfo() {
        return isInfo;
    }

    public void setInfo(boolean info) {
        isInfo = info;
    }
}