
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;

/**
 *
 * Ta klasa implementuje Socket server
 *
 */

public class Server {
        private String mes1="",mes2="";
        private int a1=21;
        private  int b1=21;
        private int a2=21;
        private int b2=21;
        private boolean endChat=false;



        public static void main(String[] args){

                ServerSocket server = null;
                /**port socket serwer-a*/
                int port = 6666;


                boolean doWeStillPlay=true;
                Server s=new Server();
                /**tworzenie socket serwer*/
                try {
                        server = new ServerSocket(port);
                } catch (IOException e) {
                        e.printStackTrace();
                }
                System.out.println("Stworzylem server");
                /**Tutaj daje wiadomosc klientom ktory byl pierwszy*/


                //******************************//
                //---- PODLACZANIE DO SERVERA---//
                //******************************//

                /*Czekam na klienta 1*/
                System.out.println("Czekam na 1 gracza");
                /**Czekam na klienta 1*/
                Socket socket1 = null;
                Socket socket2 = null;
                ObjectOutputStream oos1=null;
                ObjectOutputStream oos2=null;
                ObjectInputStream ois1=null;
                ObjectInputStream ois2=null;
                try {
                        socket1 = server.accept();
                        System.out.println("Gracz 1 dolaczyl do serwera");
                        /**Daje klientowi 1 odpowiedz*/
                        oos1 = new ObjectOutputStream(socket1.getOutputStream());
                        oos1.writeObject(true);

                        /**Teraz biore wiadomosc od klienta 1 */
                        ois1 = new ObjectInputStream(socket1.getInputStream());


                        /*Czekam na klienta 2*/
                        System.out.println("Czekam na 2 gracza");
                        /**Czekam na klienta 2*/
                        socket2 = server.accept();
                        System.out.println("Gracz 2 dolaczyl do serwera");

                        /**Konwertuje na inta*/
                        String a = (String) ois1.readObject();
                        System.out.println("Dostalem waidomosc od 1 gracza: " + a);

                        /**Daje klientowi 2 odpowiedz*/
                        oos2 = new ObjectOutputStream(socket2.getOutputStream());
                        oos2.writeObject(false);

                        /**Teraz biore wiadomosc od klienta 2 */
                        ois2 = new ObjectInputStream(socket2.getInputStream());

                        /**Konwertuje na inta*/
                        String b = (String) ois2.readObject();
                        System.out.println("Dostalem widomosc od 2 gracza: " + b);



                        /**informuje klienta 1 ze wszyscy sa*/
                        oos2.writeObject(true);

                        /**informuje klienta 2 ze wszyscy sa*/
                        oos1.writeObject(true);

                } catch (IOException e) {
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                }


                while(doWeStillPlay) {
                        /******************************************/
                        /*Otwieram strumien do gry miedzy graczami*/
                        /******************************************/
                        try {
                                s.ServerGame(ois1, ois2, oos1, oos2);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }

                        /******************************************/
                        /*Otwieram czat do rozmowy miedzy graczami*/
                        /******************************************/

                        try {
                                s.ServerChat(ois1, ois2, oos1, oos2);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }

                }


                /**zamykam wszystkie zrodla*/
                try { 
                        oos2.close();
                ois1.close();
                oos1.close();
                oos2.close();
                socket2.close();
                socket1.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }

                System.out.println("Shutting down Socket server!!");
                /**zamykam ServerSocket object*/
                try {
                        server.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        public void ServerGame(ObjectInputStream ois1, ObjectInputStream ois2, ObjectOutputStream oos1, ObjectOutputStream oos2) throws InterruptedException, IOException {
                ClientReciveGameInfo client2GameThread= new ClientReciveGameInfo(ois2, false ,this);
                ClientReciveGameInfo client1GameThread= new ClientReciveGameInfo(ois1, true ,this);


                client2GameThread.start();

                client1GameThread.start();

                a1 =21;
                a2 =21;
                b1 =21;
                b2 =21;

                while(true) {


                        System.out.println("Wysylam1 : " + a2+ ";"+b2);
                        /**Daje klientowi 1 odpowiedz*/
                        oos1.writeObject(a2);
                        oos1.writeObject(b2);
                        client2GameThread.SetLastOponnentMove(a1,b1);

                        System.out.println("Wysylam2 : " + a1+ ";"+b1);
                        /**Daje klientowi 2 odpowiedz*/
                        oos2.writeObject(a1);
                        oos2.writeObject(b1);
                        client2GameThread.SetLastOponnentMove(a2,b2);

                        if (a1 == 20 && a2 == 20 && b1 == 20 && b2 == 20) {
                                client2GameThread.interrupt();
                                client1GameThread.interrupt();
                                sleep(2000);
                                //ServerChat(socket1, socket2, ois1, ois2, oos1, oos2);
                                a1 = 21;
                                a2 = 21;
                                b1 = 21;
                                b2 = 21;
                                break;
                        }

                        sleep(100);
                }
        }
        public synchronized void ServerChat( ObjectInputStream ois1, ObjectInputStream ois2, ObjectOutputStream oos1, ObjectOutputStream oos2) throws InterruptedException {

                ClientReciveChatInfo client2Thread= new ClientReciveChatInfo(ois2, false,this );
                ClientReciveChatInfo client1Thread= new ClientReciveChatInfo( ois1, true ,this);

                client2Thread.start();
                client1Thread.start();

                /**Zmienne przechowujace wiadomosci 1 i 2 gracza*/
                endChat=false;
                while(true){

                        /**Sprawdzam czy doszlo do porozumienia miedzy graczami*/
                        if(endChat)
                        {

                                System.out.println("Wznawiam grę");
                                client2Thread.interrupt();
                                client1Thread.interrupt();

                                break;
                        }

                        /**Daje klientowi 2 odpowiedz*/
                        try {
                                oos2.writeObject(mes1);
                                System.out.println("Napisalem mes1: "+mes1);
                        } catch (IOException e) {
                                e.printStackTrace();
                        }


                        /**Daje klientowi 1 odpowiedz*/
                        try {
                                oos1.writeObject(mes2);
                                System.out.println("Napisalem mes2: "+mes2);
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                        sleep(500);

                }
        }

        /**2 metody o tej samej nazwie ale roznymi param.*/
        public void setParams(String mes,boolean isBlack1)
        {
                {
                        if (isBlack1) {
                                this.mes1 = mes;
                        } else {
                                this.mes2 = mes;
                        }
                }
                if(mes.equals("Wznawiam gre!"))endChat=true;

        }
        /**2 metody o tej samej nazwie ale roznymi param.*/
        public void setParams(int a, int b, boolean isBlack1) {
                if(isBlack1){
                        this.a1=a;
                        this.b1=b;
                }
                else{
                        this.a2=a;
                        this.b2=b;
                }
        }
}

class ClientReciveChatInfo extends Thread {
        public  ClientReciveChatInfo(ObjectInputStream ois1, boolean isBlack1,Server server1){
                this.ois=ois1;
                this.server = server1;
                this.isBlack=isBlack1;
        }


        private  ObjectInputStream ois;
        private boolean isBlack;
        private Server server;
        private String mes;

        /**
         * Biore i parsuje na String wiadomosc od 1 klienta
         */


        @Override
        public synchronized void run() {
                System.out.println("Jestem w watku CHAT");
                while(true){
                        try {
                                mes = (String) ois.readObject();
                                server.setParams(mes,isBlack);
                        } catch (IOException e) {
                        } catch (ClassNotFoundException e) {
                        } catch (ClassCastException e){
                                interrupt();
                                break;
                        }
                        if(!(mes==null||mes==""))
                        System.out.println("Dostalem widomosc od 1 gracza: " + mes);
                        try {
                                sleep(100);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                }
        }
}

class ClientReciveGameInfo extends Thread {
        public  ClientReciveGameInfo( ObjectInputStream ois1, boolean isBlack1,Server server1){
                this.ois=ois1;
                this.server = server1;
                this.isBlack=isBlack1;
        }

        private ObjectInputStream ois;
        private boolean isBlack;
        private Server server;
        private int a;
        private int b;
        private int lastA=21;
        private int lastB=21;
        /**
         * Biore i konwertuje na int wiadomosc od klienta
         */
        @Override
        public synchronized void run() {
                System.out.println("Jestem w watku GAME");
                while(true){

                        if(a==20 && b==20) {
                                System.out.println("przerywam");
                                break;
                        }
                        try {
                                /**BIore wiadomosc od klij. 1 i konwertuje na inta*/
                                a = (int) ois.readObject();
                                /**BIore wiadomosc od klij. 1 i konwertuje na inta*/
                                b = (int) ois.readObject();
                                System.out.println("Dostalem widomosc od  gracza: " + a + b);
                                server.setParams(a,b,isBlack);


                        } catch (IOException e) {
                        } catch (ClassNotFoundException e) {
                        }

                }
                stopThread();

        }
        public void SetLastOponnentMove(int a, int b)
        {
                this.lastA=a;
                this.lastB=b;
        }
        public void stopThread(){
                this.interrupt();
        }

}