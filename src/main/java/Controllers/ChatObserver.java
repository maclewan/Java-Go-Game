package Controllers;


import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    boolean doWePlay=true;
    boolean isNewMessage=false;

    private Observer observer;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;


    private String mesIn = new String("");
    private String mesOut = new String("");
    private String lastMes = new String("");

    private ChatController chatController;

    @Override
    public synchronized void run() {
        try {
            oos.writeObject("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("nowy watek powstal");
        Platform.runLater(() ->  { startChat(); });




        while(doWePlay) {
            /**Wysyłanie wiadmosci do serwera*/
            if (isNewMessage) {
                System.out.println("wysylam wiadomosc" + mesOut);
                try {
                    oos.writeObject(mesOut);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isNewMessage = false;
            }
            if(mesOut.equals("Wznawiam gre!"))
            {
                /**Odbiera wiadomosć*/
                try {
                    System.out.println("odbieram wiadomosc");
                    mesIn = (String) ois.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                int tmp=21;
                try {
                    oos.writeObject(tmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("JESTEM TUTEJ");
                observer.continueGame();
                doWePlay=false;
                this.interrupt();
                break;
            }
            if (mesIn.equals("Wznawiam gre!") ) {
                int tmp=21;
                try {
                    oos.writeObject(tmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("a tera tu");
                //TODO: MACIEUJU tutej trzeba zamknac okno ChatController
                observer.continueGame();
                doWePlay=false;
                this.interrupt();
                break;
            }
            //mesIn ="";
            /**Odbiera wiadomosć*/
            try {
                System.out.println("odbieram wiadomosc");
                mesIn = (String) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //System.out.println("Dostalem widomosc od 2 gracza: " + mesIn);
            if (!(mesIn.equals(lastMes))) {
                Platform.runLater(() -> chatController.addLabelChatText(mesIn));
                lastMes=mesIn;
                //System.out.println("jestem w if "+mesIn);
            }
            System.out.println("mesIN: "+mesIn);

            /**Usypiam watek*/
            /*try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            //endGame();
        }
        observer.continueGame();
    }

    public void endGame(){
        observer.setEndGame(true);
    }

    public void startChat() {
        try {
            Stage stage = new Stage();
            chatController = new ChatController();
            chatController.setCo(this);
            chatController.setStage(stage);


            FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("Chat.fxml"));
            loaderG.setController(chatController);

            Scene scene = new Scene(loaderG.load());
            stage.setTitle("Chat");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void continueGame(){
        mesIn="Wznawiam gre!";
        mesOut="Wznawiam gre!";
        isNewMessage=true;
        //doWePlay=false;
        //observer.continueGame();
        //this.interrupt();  /**Zabijam ten wątek chatu*/




    }

    public void setMesOut(String mesOut) {
        this.mesOut = mesOut;
        isNewMessage=true;
    }
    public void setMesIn(String mesIn) {
        this.mesIn = mesIn;
        //isNewMessage=true;
    }
}