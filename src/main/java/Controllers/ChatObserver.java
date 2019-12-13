package Controllers;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ChatObserver extends Thread{

    public ChatObserver(Socket socket, ObjectOutputStream oss, ObjectInputStream ois, Observer observer){
        this.socket=socket;
        this.oos=oss;
        this.ois=ois;
        this.observer = observer;
    }

    boolean isNewMessage=false;

    private Observer observer;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;


    String mes = new String("");

    @Override
    public synchronized void run() {





        while(true) {
            /**Wysyłanie wiadmosci do serwera*/
            if (isNewMessage) {
                try {
                    oos.writeObject(mes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isNewMessage = false;
            }


            mes = null;
            /**Odbiera wiadomosć*/
            try {
                mes = (String) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Dostalem widomosc od 2 gracza: " + mes);
            if (mes != "") {
                //todo: dopisz wiadomość do chatu
            }


            /**Usypiam watek*/
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //todo: set endGame=true;
            endGame();
        }
    }

    public void endGame(){
        observer.setEndGame(true);
    }









}
