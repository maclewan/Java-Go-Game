package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static java.lang.Thread.sleep;

public class ChatServer {
    private String mes1="";
    private String mes2="";
    boolean endChat=false;
    ObjectInputStream ois1;
    ObjectInputStream ois2;
    ObjectOutputStream oos1;
    ObjectOutputStream oos2;

    public ChatServer(ObjectInputStream ois11, ObjectInputStream ois21, ObjectOutputStream oos11, ObjectOutputStream oos21) {
        this.ois1=ois11;
        this.ois2=ois21;
        this.oos1=oos11;
        this.oos2=oos21;
    }

    public void ChatServer() throws InterruptedException {
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
}

class ClientReciveChatInfo extends Thread {
    public  ClientReciveChatInfo(ObjectInputStream ois1, boolean isBlack1,ChatServer server1){
        this.ois=ois1;
        this.server = server1;
        this.isBlack=isBlack1;
    }
    //GOTOWE//todo: pola prywatne! wszystkie, boole też

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

