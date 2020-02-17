package Controllers;


import Observers.Observer;
import Server.GameLogic.Point;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
/**
 *
 * Odpowiada za obslugę poczekalni
 *
 * */
public class WaitingFrameController{
    private Stage stage= new Stage();
    private boolean startBot=false;
    private boolean openWindow=true;

    public WaitingFrameController(boolean singlePlayer) {
        this.startBot = singlePlayer;
    }
    public WaitingFrameController() {
        this.startBot = false;
    }
    public WaitingFrameController(boolean singlePlayer,boolean openWindow) {
        this.startBot = singlePlayer;
        this.openWindow = openWindow;
    }

    @FXML
    void initialize() {

        Observer observer = new Observer(this);
        observer.start();
        if(!openWindow){
            observer.setBot(true);
        }

        if(startBot){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WaitingFrameController botController = new WaitingFrameController(false,false);
            botController.initialize();
        }
    }

    @FXML
    private Label textWaiting;


    void setStage(Stage stage) {
        this.stage = stage;
    }


    public synchronized void startGame(ClientController clientController){


        /**
         * New game
         */
        if(!openWindow){
            clientController.setBot(true);
            return;
        }


        try {
            FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("GUI.fxml"));
            loaderG.setController(clientController);
            clientController.setStage(stage);
            Scene sceneG = new Scene(loaderG.load());

            if(startBot){
                clientController.setPointToPush(new Point(128,128));
                clientController.setSthToPush(true);
            }

            stage.setTitle("Go");
            stage.setScene(sceneG);
            stage.show();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.setX(stage.getX() - 250);
            stage.setY(stage.getY() - 100);
            clientController.setYourTurnText("Zaczyna czarny");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void absentServer(){
        if(!openWindow){
            return;
        }
        textWaiting.setText("Brak aktywnego serwera");
        textWaiting.setLayoutX(textWaiting.getLayoutX()+40);
    }

    public synchronized void backToMenu(){
        if(!openWindow){
            return;
        }
        try {
            FXMLLoader loaderG = new FXMLLoader(getClass().getClassLoader().getResource("MenuFrame.fxml"));
            MenuFrameController mfc = new MenuFrameController();
            loaderG.setController(mfc);
            mfc.setStage(stage);

            Scene sceneG = new Scene(loaderG.load());

            stage.setTitle("Menu");
            stage.setScene(sceneG);
            stage.show();
            stage.setX(stage.getX()+100);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    }

