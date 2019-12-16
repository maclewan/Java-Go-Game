package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer  extends Thread  {
    private String mes1="";

    private boolean endChat=false;
    private boolean endGame=false;

    private boolean isFirstBlack=false;

    private Socket socket1;
    private ObjectInputStream ois1;
    private ObjectInputStream ois2;
    private ObjectOutputStream oos1;
    private ObjectOutputStream oos2;
    private Server s;

    public ChatServer(Server server) {
        this.s=server;
    }

    @Override
    public synchronized void run()
    {
        ServerSocket server = null;
        /**port socket serwer-a*/
        int port = 7777;

        /**tworzenie socket serwer*/

            try {
                server = new ServerSocket(port);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Problem :/");
            }
        try {
            System.out.println("Stworzylem server chatu");
            //******************************//
            //---- PODLACZANIE DO SERVERA---//
            //******************************//
            socket1 = server.accept();
            System.out.println("Gracz 1 dolaczyl do serwera");
            /**Daje klientowi 1 odpowiedz*/
            oos1 = new ObjectOutputStream(socket1.getOutputStream());
            oos1.writeObject(true);

            /**Teraz biore wiadomosc od klienta 1 */
            ois1 = new ObjectInputStream(socket1.getInputStream());


            /**Czekam na klienta 2*/
            System.out.println("Czekam na 2 gracza");
            /**Czekam na klienta 2*/
            Socket socket2 = server.accept();
            System.out.println("Gracz 2 dolaczyl do serwera");

            /**Konwertuje na inta*/
            isFirstBlack = (boolean) ois1.readObject();
            if(isFirstBlack)System.out.println("1 podlaczony jest czarny");

            /**Daje klientowi 2 odpowiedz*/
            oos2 = new ObjectOutputStream(socket2.getOutputStream());
            oos2.writeObject(false);

            /**Teraz biore wiadomosc od klienta 2 */
            ois2 = new ObjectInputStream(socket2.getInputStream());

            /**Konwertuje na inta*/
            boolean tmp = (boolean) ois2.readObject();
            if(tmp)System.out.println("2 podlaczony gracz jest czarny" );


            /**informuje klienta 1 ze wszyscy sa*/
            oos2.writeObject(true);

            /**informuje klienta 2 ze wszyscy sa*/
            oos1.writeObject(true);


            ServerChat();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



    private void ServerChat() throws InterruptedException {
        ClientReciveChatInfo client2Thread= new ClientReciveChatInfo(ois2, false,this );
        ClientReciveChatInfo client1Thread= new ClientReciveChatInfo( ois1, true ,this);

        client2Thread.start();
        client1Thread.start();

        /**Zmienne przechowujace wiadomosci 1 i 2 gracza*/
        endChat=false;
        while(true){




            /**Wysylam graczom wiadomosci*/
            try {
                oos2.writeObject(mes1);
                oos1.writeObject(mes1);

            } catch (IOException e) {
                e.printStackTrace(); }

            /**Zawieszenie chatu*/
            if(endChat)
            {
                System.out.println("Wznawiam grę");
                stopServer();
                this.interrupt();
            }
            /**Koniec gry*/
            if(endGame)
            {
                System.out.println("Koniec gry");
                stopServer();
                this.interrupt();
            }

            sleep(100);
        }
    }

    /**2 metody o tej samej nazwie ale roznymi param.*/
    public void setMessageToSend(String mes,boolean isBlack)
    {
        /**gdy wznawiam gre, wysylam info kto powinien zaczac*/
        if(mes.equals("Wznawiam gre!")){
            endChat=true;
            s.setIsBlack(isBlack);
            this.mes1=mes;
        }
        if(mes.equals("Koniec gry!")){
            endChat=true;
            this.mes1=mes;
        }


        else if(isBlack){
            this.mes1=("Czarny:\t "+ mes);
        }
        else
            this.mes1=("Bialy:\t "+ mes);





    }

    private void stopServer() {
        try {
            socket1.close();
            ois1.close();
            ois2.close();
            oos1.close();
            oos2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class ClientReciveChatInfo extends Thread {

    public  ClientReciveChatInfo(ObjectInputStream ois1, boolean isBlack1,ChatServer server){
        this.ois = ois1;
        this.server = server;
        this.isBlack = isBlack1;
    }

    private ObjectInputStream ois;
    private boolean isBlack;
    private ChatServer server;
    private String mes;

    /**
     * Biore i konwertuje na String wiadomosc od klienta
     */
    @Override
    public synchronized void run() {

        System.out.println("Jestem w watku chatRecive");

        while(true){
            try {
                mes = (String) ois.readObject();
                server.setMessageToSend(mes,isBlack);

            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
            System.out.println("Dostalem widomosc od gracza: " + mes);
            if(mes.equals("Wznawiam gre!")||mes.equals("Koniec gry!")){
                this.interrupt();
            }
        }
    }
}

