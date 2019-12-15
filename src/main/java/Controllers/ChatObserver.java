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

            if (isNewMessage) {
                System.out.println("wysylam wiadomosc " + mesOut);
                try {
                    oos.writeObject(mesOut);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isNewMessage = false;
            }
            if(mesOut.equals("Wznawiam gre!"))
            {

    /*            try {
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
                System.out.println("JESTEM TUTaJ");*/
/*
                observer.continueGame();
                doWePlay=false;
                this.interrupt();
                break;
*/
                wznowGre();
                break;
            }
            if (mesIn.equals("Wznawiam gre!") ) {
                wznowGre();
                break;
            }
            /**Odbiera wiadomosć*/
            try {
                mesIn = (String) ois.readObject();
                if(!(mesIn==null||mesIn.equals(""))){
                    System.out.println("odbieram wiadomosc");

                }
            } catch (IOException e)  {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {

                e.printStackTrace();
            } catch ( ClassCastException e){
                wznowGre();
                break;
            }

            if (!(mesIn.equals(lastMes))) {
                Platform.runLater(() -> chatController.addLabelChatText(mesIn));
                lastMes=mesIn;

            }
            if(!(mesIn==null||mesIn.equals(""))) {
                System.out.println("mesIN: =" + mesIn+"=");
            }
            /**temp*/
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
    }

    public void wznowGre(){


        int tmp=21;
        try {
            oos.writeObject(tmp);
        } catch (IOException e) {
            e.printStackTrace();
        }


        chatController.closeChat();
        observer.continueGame();
        doWePlay=false;
        this.interrupt();


    }
}