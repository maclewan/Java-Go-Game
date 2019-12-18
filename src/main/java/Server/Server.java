
package Server;

import Server.GameLogic.Board;
import Server.GameLogic.Point;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 *
 * Ta klasa implementuje Socket Server
 *
 */
/**
 *
 * server gry- główny
 *
 * */
public class Server {
    private Point newPoint = new Point(21,21);
    private Point oldPoint = new Point(21,21);

    private ArrayList<Point> pointList = new ArrayList<>();
    private Board board;
    private boolean isGameActive=true;
    private boolean isGameOn=true;
    private boolean isBlack=true;
    private boolean botGame=false;
    private ObjectOutputStream oos1;
    private ObjectOutputStream oos2;
    private ArrayList<Point> tempList;

    private Server() {
    }
    public static Server getInstance() {
        return ServerHolder.INSTANCE;
    }

    private static class ServerHolder {
        private static final Server INSTANCE = new Server();
    }
    /*Pozwala na utworzenie tylko jednego obiektu danej klasy i zapewnienie do niego globalnego dostępu.
    Zawiera:
    statyczną zmienną przechowującą instancję tej klasy,
    prywatny „pusty” konstruktor (aby nie można było utworzyć nowego obiektu tej klasy z wykorzystaniem operatora „new”),
    publiczną statyczną metodę „getInstance” bez żadnych parametrów.

    gdy klasa Server zostanie załadowana,
    klasa ServerHolder nie zostanie wczytana
    do pamięci dopóki nie zostanie wywołana metoda getInstance().
    Takie podejście nie wymaga synchronizacji

    Przestrzegana konw. nazewnictwa:
    metody male litery
    klasy duze
    obikty gui np. przycisk nazywany: btnResume

    */
    public static void main(String[] args){

        ServerSocket server = null;
        /**port socket serwer-a*/
        int port = 6666;


        Server s=new Server();
        /**tworzenie socket serwer*/
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Stworzylem Server");
        /**Tutaj daje wiadomosc klientom ktory byl pierwszy*/


        //******************************//
        //---- PODLACZANIE DO SERVERA---//
        //******************************//

        /*Czekam na klienta 1*/
        System.out.println("Czekam na 1 gracza");
        /**Czekam na klienta 1*/
        Socket socket1;
        Socket socket2;
        ObjectInputStream ois1;
        ObjectInputStream ois2;
        try {
            socket1 = server.accept();
            System.out.println("Gracz 1 dolaczyl do serwera");
            /**Daje klientowi 1 odpowiedz*/
            s.oos1 = new ObjectOutputStream(socket1.getOutputStream());
            s.oos1.writeObject(true);

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
            s.oos2 = new ObjectOutputStream(socket2.getOutputStream());
            s.oos2.writeObject(false);

            /**Teraz biore wiadomosc od klienta 2 */
            ois2 = new ObjectInputStream(socket2.getInputStream());

            /**Konwertuje na stringa*/
            String b = (String) ois2.readObject();
            System.out.println("Dostalem widomosc od 2 gracza: " + b);



            /**informuje klienta 1 ze wszyscy sa*/
            s.oos2.writeObject(true);

            /**informuje klienta 2 ze wszyscy sa*/
            s.oos1.writeObject(true);





            /******************************************/
            /*Otwieram strumien do gry miedzy graczami*/
            /******************************************/

            /**Pętla uruchamiajaca gre bez resetu serwera*/
            s.ServerGame(ois1, ois2, s.oos1, s.oos2);


            /**zamykam wszystkie zrodla*/
            s.oos2.close();
            ois1.close();
            s.oos1.close();
            s.oos2.close();
            socket2.close();
            socket1.close();


            System.out.println("Shutting down Socket Server!");
            server.close();

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void ServerGame(ObjectInputStream ois1, ObjectInputStream ois2, ObjectOutputStream oos1, ObjectOutputStream oos2) throws InterruptedException, IOException {

        ClientReciveGameInfo client2GameThread= new ClientReciveGameInfo(ois2, false ,this);
        ClientReciveGameInfo client1GameThread= new ClientReciveGameInfo(ois1, true ,this);

        ChatServer chatServer = new ChatServer(this);
        chatServer.start();

        client2GameThread.start();
        client1GameThread.start();

        board = new Board(this);

        pointList = new ArrayList<>();
        board.startArrayOfCheckers();

        while(isGameOn) {
            /**Czas na ruch bota*/
            if(botGame&&!isBlack){
                board.botMove();

            }

            /**wysylam nowy punkt o ile taki jest*/
            if(newPoint.getY()<20&&newPoint.getX()<20) {

                board.addChecker(newPoint.getX(),newPoint.getY(),newPoint.isBlack());
                newPoint=new Point(99,99);
            }
            /**pasowanie tury*/
            if(newPoint.getY()==20&&newPoint.getX()==20){
                changePlayer();
                if(isBlack)
                    sendSignal(51);
                else
                    sendSignal(50);
                newPoint=new Point(69,69);
            }
            /**sygnal poczatkowy o grze z botem*/
            if(newPoint.getX()==128){
                botGame=true;
                newPoint=new Point(99,99);
                continue;
            }
            /**zawieszanie gry*/
            if(oldPoint.getX()==newPoint.getX()&&oldPoint.getX()==69){
                isGameActive=false;
                /**w przypadku gry z botem koniec gry*/
                if(botGame){
                    isGameActive=false;
                    sendSignal(999);
                    continue;
                }
                /**w przypadku gry bez bota otworz chat*/
                newPoint=new Point(99,99);
                ArrayList<Point> temp =new ArrayList<>();
                temp.add(new Point(69,69));
                oos1.writeObject(temp);
                oos2.writeObject(temp);
                continue;

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
            else
                System.out.println("Wiadomosc nieprzeszla");
        }
        else
            System.out.println("Wiadomosc nieprzeszla - blokada gry - chat");


    }


    public void setPointList(ArrayList<Point> list){
        this.pointList = list;
    }


    public void changePlayer(){
        isBlack=!isBlack;

        if(isBlack)
            sendSignal(41);
        else
            sendSignal(40);
    }

    public void sendSignal(int signal){
        ArrayList<Point> temp =new ArrayList<>();
        temp.add(new Point(signal,signal));
        try {
        oos1.writeObject(temp);
        oos2.writeObject(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setIsBlack(boolean black) {
        isBlack = black;
        isGameActive=true;
    }

    public void endCurrentGame(){
    }

}



/**
 *
 * odbieranie info gry
 *
 * */
class ClientReciveGameInfo extends Thread {
    ClientReciveGameInfo(ObjectInputStream ois, boolean isBlack1, Server server1){
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

            } catch (IOException | ClassNotFoundException ignored) {
            }
        }
    }

}