package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer  extends Thread  {
    private String mes1="";
    private String mes2="";
    private boolean endChat=false;
    boolean isChatActive=false;
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
            }
        try {
            System.out.println("Stworzylem server chatu");
            //******************************//
            //---- PODLACZANIE DO SERVERA---//
            //******************************//
            Socket socket1 = server.accept();
            System.out.println("Gracz 1 dolaczyl do serwera");
            /**Daje klientowi 1 odpowiedz*/
            oos1 = new ObjectOutputStream(socket1.getOutputStream());
            oos1.writeObject(true);

            /**Teraz biore wiadomosc od klienta 1 */
            ois1 = new ObjectInputStream(socket1.getInputStream());


            /*Czekam na klienta 2*/
            System.out.println("Czekam na 2 gracza");
            /**Czekam na klienta 2*/
            Socket socket2 = server.accept();
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


    }



    private void ServerChat() throws InterruptedException {
        ClientReciveChatInfo client2Thread= new ClientReciveChatInfo(ois2, false,this );
        ClientReciveChatInfo client1Thread= new ClientReciveChatInfo( ois1, true ,this);

        client2Thread.start();
        client1Thread.start();

        /**Zmienne przechowujace wiadomosci 1 i 2 gracza*/
        endChat=false;
        while(true){

            /**Zawieszenie gry*/
            if(endChat)
            {
                System.out.println("Wznawiam grę");
                isChatActive=false;
                s.gameDoor();
            }
            sleep(100);
            /**Daje klientowi 2 odpowiedz*/
            try {
                oos2.writeObject(mes1);
            } catch (IOException e) {
                e.printStackTrace();
            }



            /**Daje klientowi 1 odpowiedz*/
            try {
                oos1.writeObject(mes2);
            } catch (IOException e) {
                e.printStackTrace();
            }

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
    /**2 otwiera/zamyka chat*/
    public void chatDoor() { isChatActive=!isChatActive; }
}

class ClientReciveChatInfo extends Thread {
    public  ClientReciveChatInfo(ObjectInputStream ois1, boolean isBlack1,ChatServer server1){
        if(server1.isChatActive) {
            this.ois = ois1;
            this.server = server1;
            this.isBlack = isBlack1;
        }
        else  System.out.println("Czat nie aktywny, graj a nie piszesz");
    }
    private  ObjectInputStream ois;
    private boolean isBlack;
    private ChatServer server;
    private String mes;
    /**
     * Biore i konwertuje na String wiadomosc od 1 klienta
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
            }
            System.out.println("Dostalem widomosc od 1 gracza: " + mes);
        }
    }
}

