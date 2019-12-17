/**
 *
 * Testy dot. zasad gry
 *
 * */
package gameRulesTests;

import Server.GameLogic.Board;
import Server.GameLogic.Point;
import Server.Server;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * Test na zabicie oka w lewym gornym rogu
 *
 * */
public class KillEyeInCornerTest{

    Server s = new Server();
    boolean isFirst=false;

    @Test
    public void test() {
        server a= new server();
        a.start();

        //player player1= new player();
        //player player2= new player();
        //player1.start();
        //player2.start();

        Board test= new Board(null);
        test.startArrayOfCheckers();
        test.addChecker(0, 1, false);
        test.addChecker(1, 0, false);
        test.addChecker(1, 1, false);
        test.addChecker(2, 0, true);
        test.addChecker(2, 1, true);
        test.addChecker(1, 2, true);
        test.addChecker(0, 2, true);
        /*temp[0][17].setStroke(Color.WHITE);
        temp[1][18].setStroke(Color.WHITE);*/


        test.addChecker(0, 0, true);
     //   test.checkers[0][0]=pc;
        assert(test.getChecker(0,0)==1) && (test.getChecker(1,1)==2) && (test.getChecker(1,0)==2) && (test.getChecker(0,1)==2);

    }
    public class server extends Thread {
        @Override
        public synchronized void run() {

            String[] args=null;
            s.main(args);
        }
    }
    public class player extends Thread {
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
        @Override
        public synchronized void run() {

            /**
             *
             * Etap laczenia sie 2 klientow
             *
             * */

            try {
                host = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            /**Lacze z serwerrem*/
            /**skonfiguruj polaczenie socket do servera*/
            try {
                socket = new Socket(host.getHostName(), 6666);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /**odbierz odpowiedz serwera*/
            try {
                ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Dolaczylem sb do servera");
            isFirst=true;

            /**Tutaj sprawdzam czy to byl pierwszy*/
            try {
                boolean setIsBlack = (boolean) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


            /**napisz do socket uzywajac ObjectOutputStream*/
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject("OK");
            } catch (IOException e) {
                e.printStackTrace();
            }


            /**Odbierz wiadomosc nt 2 gracza*/
            /**odbierz odpowiedz serwera*/
            boolean isSecond = false;       //tu czeka na drugiego gracza
            try {
                isSecond = (boolean) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if(isSecond){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /*chatObserver = new ChatObserver();
            chatObserver.start();*/

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
