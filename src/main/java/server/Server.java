
package server;

import Controllers.ChatController;
import client.Point;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 *
 * Ta klasa implementuje Socket server
 *
 */

public class Server {
        private String mes1="",mes2="";
        private Point newPoint = new Point(21,21);
        private Point oldPoint = new Point(21,21);

        private ArrayList<Point> pointList = new ArrayList<>();
        private Board board;
        private boolean isGameActive=true;
        private boolean isGameOn=true;
        private boolean isBlack=true;
        private ChatServer chat = new ChatServer(this);
        private ArrayList<Point> tempList;

        public static void main(String[] args){

                ServerSocket server = null;
                /**port socket serwer-a*/
                int port = 6666;


                Server s=new Server();                //todo: może zrobić z tego wzorzec singleton? - nie jest to trudne, a można w to ubrać - serwer powinien byc tylko jeden, sratatatata xD
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

                        /**Konwertuje na stringa*/
                        String b = (String) ois2.readObject();
                        System.out.println("Dostalem widomosc od 2 gracza: " + b);



                        /**informuje klienta 1 ze wszyscy sa*/
                        oos2.writeObject(true);

                        /**informuje klienta 2 ze wszyscy sa*/
                        oos1.writeObject(true);

                        /******************************************************/
                        /*W tle wlaczam server czatu aby mogli sie juz polaczyc*/
                        /*******************************************************/
                        s.chat.start();

                        /******************************************/
                        /*Otwieram strumien do gry miedzy graczami*/
                        /******************************************/

                        s.ServerGame(ois1, ois2, oos1, oos2);


                        /**zamykam wszystkie zrodla*/
                        oos2.close();
                        ois1.close();
                        oos1.close();
                        oos2.close();
                        socket2.close();
                        socket1.close();


                        System.out.println("Shutting down Socket server!");
                        server.close();

                } catch (IOException e) {
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }


        }

        private void ServerGame(ObjectInputStream ois1, ObjectInputStream ois2, ObjectOutputStream oos1, ObjectOutputStream oos2) throws InterruptedException, IOException {

                ClientReciveGameInfo client2GameThread= new ClientReciveGameInfo(ois2, false ,this);
                ClientReciveGameInfo client1GameThread= new ClientReciveGameInfo(ois1, true ,this);

                client2GameThread.start();
                client1GameThread.start();

                board = new Board(this);

                pointList = new ArrayList<>();
                board.startArrayOfCheckers();

                while(isGameOn) {

                        /**wysylam nowy punkt o ile taki jest*/
                        if(newPoint.getY()<20&&newPoint.getX()<20) {

                                board.addChecker(newPoint.getX(),newPoint.getY(),newPoint.isBlack());
                                newPoint=new Point(99,99);
                        }
                        /**pasowanie tury*/
                        if(newPoint.getY()==20&&newPoint.getX()==20){
                                changePlayer();
                                newPoint=new Point(69,69);                   //oba sygnały 69 oznacząc będą zawiesznie gry
                        }
                        /**zawieszanie gry*/
                        if(oldPoint.getX()==newPoint.getX()&&oldPoint.getX()==69){
                                isGameActive=false;
                                tempList.clear();
                                tempList.add(new Point(69,69));
                                ChatServer chatServer = new ChatServer(this);
                                chatServer.start();

                        }




                        tempList=pointList;

                        if(tempList.size()>0)
                        System.out.println("Wysylam1 liste " + tempList);
                        /**Daje klientowi 1 odpowiedz*/
                        oos1.writeObject(tempList);

                        if(tempList.size()>0)
                        System.out.println("Wysylam2 liste " + tempList);
                        /**Daje klientowi 2 odpowiedz*/
                        oos2.writeObject(tempList);
                        tempList.clear();





                        sleep(100);
                }
        }


        public void setParams(Point point, boolean isBlack) {
                if(isGameActive) {
                        if (this.isBlack == isBlack) {
                                oldPoint=newPoint;
                                newPoint = point;
                                System.out.println("Wiadomosc przeszla");
                        }
                }
                else
                        System.out.println("Wiadomosc nieprzeszla");


        }


        public void setPointList(ArrayList<Point> list){

                this.pointList = list;
        }



        private void endChatMode(int whosTurn){
                pointList = new ArrayList<>();

        }
        private void endGame(){
                isGameActive=false;
        }
        public void changePlayer(){
                isBlack=!isBlack;
        }

        public void setIsBlack(boolean black) {
                isBlack = black;
        }
}



class ClientReciveGameInfo extends Thread {
        public  ClientReciveGameInfo( ObjectInputStream ois, boolean isBlack1,Server server1){
                this.ois=ois;
                this.server = server1;
                this.isBlack=isBlack1;
        }

        private ObjectInputStream ois;
        private boolean isBlack;
        private Server server;
        private Point point = new Point(21,21);
        private Point lastPoint = new Point(21,21);

        /**
         * Biore i konwertuje na int wiadomosc od klienta
         */
        @Override
        public synchronized void run() {
                System.out.println("Jestem w watku GAME");
                while(true){
                        try {
                                /**Biore wiadomosc od klij. 1 i parsuje na Point*/
                                point = (Point) ois.readObject();

                                System.out.println("Dostalem widomosc od  gracza: " + point.getX() +";"+ point.getY());
                                server.setParams(point,isBlack);

                        } catch (IOException e) {
                        } catch (ClassNotFoundException e) {
                        }
                }
        }





}