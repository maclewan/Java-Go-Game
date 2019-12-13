package Controllers;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ChatObserver extends Thread{

    public ChatObserver(Socket socket, ObjectOutputStream oss, ObjectInputStream ois){
        this.socket=socket;
        this.oos=oss;
        this.ois=ois;
    }

    boolean isNewMessage=false;


    Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    String mes;

    @Override
    public synchronized void run() {
        while(true) {

            mes = null;
            /**Odbiera wiadomosć*/
            try {
                mes = (String) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Dostalem wiadomosc od serwera: " + mes);
            if (mes != "") {
                //todo: dopisz wiadomość do chatu
            }


            /**Wysyłanie wiadmosci do serwera*/
            if (isNewMessage) {
                try {
                    oos.writeObject(mes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isNewMessage = false;
            }


            /**Usypiam watek*/
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }








    
}
