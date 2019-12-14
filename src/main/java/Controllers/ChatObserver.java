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

    boolean isNewMessage=false;

    private Observer observer;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;


    private String mesIn = new String("");
    private String mesOut = new String("");

    private ChatController chatController;

    @Override
    public synchronized void run() {

        Platform.runLater(() ->  {
            startChat();
        });




        while(true) {
            /**Wysyłanie wiadmosci do serwera*/
            if (isNewMessage) {
                System.out.println("wysylam wiadomosc");
                try {
                    oos.writeObject(mesOut);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isNewMessage = false;
            }


            mesIn ="";
            /**Odbiera wiadomosć*/
            try {
                System.out.println("odbieram wiadomosc");
                mesIn = (String) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Dostalem widomosc od 2 gracza: " + mesIn);
            if (mesIn != "") {
                Platform.runLater(() -> chatController.addLabelChatText(mesIn));
                //System.out.println("jestem w if "+mesIn);
            }
            System.out.println(mesIn);

            /**Usypiam watek*/
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            endGame();
        }
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
        observer.continueGame();
        this.interrupt();  /**Zabijam ten wątek chatu*/


    }

    public void setMesOut(String mesOut) {
        this.mesOut = mesOut;
        isNewMessage=true;
    }
}
